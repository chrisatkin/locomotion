set title "bloom filter, vector addition"
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

plot "< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 10'" using 3:4 with linespoints title "vector-length=10" ls 1 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 20'" using 3:4 with linespoints title "vector-length=20" ls 2 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 30'" using 3:4 with linespoints title "vector-length=30" ls 3 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 40'" using 3:4 with linespoints title "vector-length=40" ls 10 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 50'" using 3:4 with linespoints title "vector-length=50" ls 4 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 60'" using 3:4 with linespoints title "vector-length=60" ls 5 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 70'" using 3:4 with linespoints title "vector-length=70" ls 6 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 80'" using 3:4 with linespoints title "vector-length=80" ls 7 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 90'" using 3:4 with linespoints title "vector-length=90" ls 8 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 100'" using 3:4 with linespoints title "vector-length=100" ls 9 axes x1y1