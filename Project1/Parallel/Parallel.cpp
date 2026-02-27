#include <iostream>
//for write on file (file stream)
#include <fstream>
#include <iomanip>
#include <omp.h>

const int N = 500;
const int T = 500;
const int SNAP_EVERY = 50;
const double HOT = 100.0;

void init_rod(double u[N]) {
    for (int i = 0; i < N; i++) u[i] = 0.0;
    u[N / 2] = HOT;
}

//for writing values on the CSV files 
//ofstream = output file stream
void write_snapshot(std::ofstream& out, int t, const double u[N]) {
    out << t;
    out << std::setprecision(10);
    for (int i = 0; i < N; i++) {
        out << "," << u[i];
    }
    out << "\n";
}

// Serial run: writes CSV, returns COMPUTE time only (I/O excluded)
double run_serial_and_write_csv(const char* filename) {
    double cur[N];// array for t 
    double next[N];//array for t+1

    init_rod(cur);

    //open the file
    std::ofstream out(filename);

    if (!out) throw std::runtime_error("Failed to open heat_output.csv for writing");

    write_snapshot(out, 0, cur);

    double compute_seconds = 0.0;

    for (int t = 1; t <= T; t++) {
        double t0 = omp_get_wtime();
        //Boundaries are fixed
        next[0] = cur[0];
        next[N - 1] = cur[N - 1];
        //interior updates
        for (int i = 1; i < N - 1; i++) {
            next[i] = 0.5 * (cur[i - 1] + cur[i + 1]);
        }
        //next will be current state after a time step
        for (int i = 0; i < N; i++) {
            cur[i] = next[i];
        }

        double t1 = omp_get_wtime();
        compute_seconds += (t1 - t0);
        //write after 50 timesteps because I\O is slow
        if (t % SNAP_EVERY == 0) {
            write_snapshot(out, t, cur);
        }
    }

    return compute_seconds;
}

// Parallel run: compute only (no CSV). Returns compute time.
double run_parallel_compute_only(int threads) {
    double cur[N];
    double next[N];
    init_rod(cur);

    omp_set_num_threads(threads);

    double t0 = omp_get_wtime();

    for (int t = 1; t <= T; t++) {
        // boundaries fixed (do it serially)
        next[0] = cur[0];
        next[N - 1] = cur[N - 1];

        // parallelize spatial update
#pragma omp parallel for
        for (int i = 1; i < N - 1; i++) {
            next[i] = 0.5 * (cur[i - 1] + cur[i + 1]);
        }

        // copy back (could also be parallel, but it's fine either way)
#pragma omp parallel for
        for (int i = 0; i < N; i++) {
            cur[i] = next[i];
        }
    }

    double t1 = omp_get_wtime();
    return (t1 - t0);
}

int main() {
    try {
        // 1) Sequential baseline (also writes CSV for visualization)
        double tseq = run_serial_and_write_csv("heat_output.csv");

        std::cout << "Sequential run done.\n";
        std::cout << "Sequential compute time (s): " << tseq << "\n";
        std::cout << "===============================================\n\n";

        // 2) Parallel performance runs
        const int thread_counts[4] = { 1, 2, 4, 8 };
        
        //left : left align
        //setw(10)=set width to 10 chars
        std::cout << std::left
            << std::setw(10) << "Threads"
            << std::setw(15) << "Time(s)"
            << std::setw(15) << "Speedup"
            << std::setw(15) << "Efficiency"
            << "\n";

        //a string of 55 dashes.
        std::cout << std::string(55, '-') << "\n";

        for (int k = 0; k < 4; k++) {
            int numOfThreads = thread_counts[k];
            double tpar = run_parallel_compute_only(numOfThreads);

            double speedup = tseq / tpar;
            double efficiency = speedup / numOfThreads;

            std::cout << std::left
                << std::setw(10) << numOfThreads
                << std::setw(15) << tpar
                << std::setw(15) << speedup
                << std::setw(15) << efficiency
                << "\n";
        }
    }
    catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << "\n";
        return 1;
    }
    return 0;
}