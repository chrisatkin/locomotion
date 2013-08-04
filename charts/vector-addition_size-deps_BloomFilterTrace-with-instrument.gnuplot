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

plot "< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 100'" using 3:4 with linespoints title "vector-length=100" ls 1 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 200'" using 3:4 with linespoints title "vector-length=200" ls 2 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 300'" using 3:4 with linespoints title "vector-length=300" ls 3 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 400'" using 3:4 with linespoints title "vector-length=400" ls 10 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 500'" using 3:4 with linespoints title "vector-length=500" ls 4 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 600'" using 3:4 with linespoints title "vector-length=600" ls 5 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 700'" using 3:4 with linespoints title "vector-length=700" ls 6 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 800'" using 3:4 with linespoints title "vector-length=800" ls 7 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 900'" using 3:4 with linespoints title "vector-length=900" ls 8 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/vector-addition-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 1000'" using 3:4 with linespoints title "vector-length=100" ls 9 axes x1y1