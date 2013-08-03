set title "bloom filter, none dependent"
set xlabel "bit vector length"
set ylabel "dependencies"
set grid
set xtics nomirror
set ytics nomirror
set key top right

set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81

plot "< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 1000'" using 3:4 with linespoints title "accesses=1000" ls 1 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 2000'" using 3:4 with linespoints title "accesses=2000" ls 2 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 3000'" using 3:4 with linespoints title "accesses=3000" ls 3 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 4000'" using 3:4 with linespoints title "accesses=4000" ls 10 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 5000'" using 3:4 with linespoints title "accesses=5000" ls 4 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 6000'" using 3:4 with linespoints title "accesses=6000" ls 5 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 7000'" using 3:4 with linespoints title "accesses=7000" ls 6 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 8000'" using 3:4 with linespoints title "accesses=8000" ls 7 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 9000'" using 3:4 with linespoints title "accesses=9000" ls 8 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/none-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 10000'" using 3:4 with linespoints title "accesses=10000" ls 9 axes x1y1