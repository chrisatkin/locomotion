set title "bloom filter, all dependent"
set xlabel "bit vector length"
set ylabel "execution time (ms)"
set grid
set xtics nomirror
set ytics nomirror
set key top right

set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81

plot "< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 100000'" using 3:($5/1000000) with linespoints title "accesses=1000" ls 1 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 200000'" using 3:($5/1000000) with linespoints title "accesses=2000" ls 2 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 300000'" using 3:($5/1000000) with linespoints title "accesses=3000" ls 3 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 400000'" using 3:($5/1000000) with linespoints title "accesses=4000" ls 10 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 500000'" using 3:($5/1000000) with linespoints title "accesses=5000" ls 4 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 600000'" using 3:($5/1000000) with linespoints title "accesses=6000" ls 5 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 700000'" using 3:($5/1000000) with linespoints title "accesses=7000" ls 6 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 800000'" using 3:($5/1000000) with linespoints title "accesses=8000" ls 7 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 900000'" using 3:($5/1000000) with linespoints title "accesses=9000" ls 8 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=false-storage=BloomFilterTrace | awk '$1 == 1000000'" using 3:($5/1000000) with linespoints title "accesses=10000" ls 9 axes x1y1