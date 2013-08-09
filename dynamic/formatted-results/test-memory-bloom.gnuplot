set title "none dependent, bloom filter"
set xlabel "bit vector length"
set ylabel "memory usage (kb)"
set grid
set xtics nomirror
set ytics nomirror
set key top right

plot "< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 1000'" using 3:($2/1024) with linespoints title "accesses=1000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 2000'" using 3:($2/1024) with linespoints title "accesses=2000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 3000'" using 3:($2/1024) with linespoints title "accesses=3000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 4000'" using 3:($2/1024) with linespoints title "accesses=4000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 5000'" using 3:($2/1024) with linespoints title "accesses=5000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 6000'" using 3:($2/1024) with linespoints title "accesses=6000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 7000'" using 3:($2/1024) with linespoints title "accesses=7000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 8000'" using 3:($2/1024) with linespoints title "accesses=8000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 9000'" using 3:($2/1024) with linespoints title "accesses=9000" lw 2,\
	"< sort -k1,1 -n -k3,3 -n test-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 10000'" using 3:($2/1024) with linespoints title "accesses=10000" lw 2