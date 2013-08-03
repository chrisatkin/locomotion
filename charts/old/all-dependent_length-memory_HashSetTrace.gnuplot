set title "all dependent"
set xlabel "length"
set ylabel "memory usage (kb)"
set grid

set style line 1 lt 1 lw 2
set style line 2 lt 2 lw 2

plot	"< sort -n ../formatted-results/all-dependent-instrumentation=true-storage=HashSetTrace" using 1:($2/1024) ls 1 title "with instrument" with lines,\
	"< sort -n ../formatted-results/all-dependent-instrumentation=false-storage=HashSetTrace" using 1:($2/1024) ls 2 title "no instrument" with lines