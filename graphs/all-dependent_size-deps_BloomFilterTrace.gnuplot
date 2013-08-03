set title "bloom filter, fractional dependent"
set xlabel "bit vector"
set ylabel "dependencies"
set y2label "memory usage (kb)"
set grid

set style line 1 lt 1 lw 2
set style line 2 lt 2 lw 2

set y2tics nomirror

plot "< sort -k1,1 -n -k3,3 -n ../formatted-results/fractional-dependent-instrumentation=true-storage=BloomFilterTrace | awk '$1 == 10000'" using 3:4 with lines title "dependencies" ls 1 axes x1y1, \
	"" using 3:($2/1024) with lines title "memory" axes x1y2