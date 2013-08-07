set title "hash set, all dependent"
set xlabel "accesses"
set ylabel "execution time"
set grid
set xtics nomirror
set ytics nomirror
set key top right

plot "< sort -n ../formatted-results/all-dependent-instrumentation=false-storage=HashSetTrace" using 1:($3/1000000) with linespoints title "no-instrument" ls 1,\
	 "< sort -n ../formatted-results/all-dependent-instrumentation=true-storage=HashSetTrace" using 1:($3/1000000) with linespoints title "with-instrument" ls 2