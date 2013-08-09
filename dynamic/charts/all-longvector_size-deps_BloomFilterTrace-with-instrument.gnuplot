set title "bloom filter, all dependent"
set xlabel "accesses length"
set ylabel "dependencies"
set grid
set xtics nomirror
set ytics nomirror
set key top right

set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81

plot "< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 100000'" using 1:4 with linespoints title "bit-vector=1000" ls 1 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 200000'" using 1:4 with linespoints title "bit-vector=2000" ls 2 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 300000'" using 1:4 with linespoints title "bit-vector=3000" ls 3 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 400000'" using 1:4 with linespoints title "bit-vector=4000" ls 10 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 500000'" using 1:4 with linespoints title "bit-vector=5000" ls 4 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 600000'" using 1:4 with linespoints title "bit-vector=6000" ls 5 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 700000'" using 1:4 with linespoints title "bit-vector=7000" ls 6 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 800000'" using 1:4 with linespoints title "bit-vector=8000" ls 7 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-smallvector-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 900000'" using 1:4 with linespoints title "bit-vector=9000" ls 8 axes x1y1