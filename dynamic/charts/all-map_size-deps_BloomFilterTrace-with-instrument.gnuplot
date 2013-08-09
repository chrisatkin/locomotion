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

set logscale y

plot	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 10000'" using 1:4 with linespoints title "bit-vector=10000" ls 1 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 110000'" using 1:4 with linespoints title "bit-vector=110000" ls 2 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 210000'" using 1:4 with linespoints title "bit-vector=210000" ls 3 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 310000'" using 1:4 with linespoints title "bit-vector=310000" ls 10 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 410000'" using 1:4 with linespoints title "bit-vector=410000" ls 4 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 510000'" using 1:4 with linespoints title "bit-vector=510000" ls 5 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 610000'" using 1:4 with linespoints title "bit-vector=610000" ls 6 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 710000'" using 1:4 with linespoints title "bit-vector=710000" ls 7 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 810000'" using 1:4 with linespoints title "bit-vector=810000" ls 8 axes x1y1, \
	"< sort -k1,1 -n -k3,3 -n ../formatted-results/all-map-instrumentation=true-storage=BloomFilterTrace | awk '$3 == 910000'" using 1:4 with linespoints title "bit-vector=910000" ls 8 axes x1y1