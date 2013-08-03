set multiplot layout 1,2 title "memory usage"

set title "all dependent"
set xlabel "length"
set ylabel "memory usage (kb)"
set grid
plot	"< sort -n ../formatted-results/all-dependent-instrumentation=true-storage=HashSetTrace" using 1:($2/1024) title "with-instrument" with lines,\
	"< sort -n ../formatted-results/all-dependent-instrumentation=false-storage=HashSetTrace" using 1:($2/1024) title "instrument" with lines

set title "none dependent"
plot	"< sort -n ../formatted-results/none-dependent-instrumentation=true-storage=HashSetTrace" using 1:($2/1024) title "with-instrument" with lines,\
	"< sort -n ../formatted-results/none-dependent-instrumentation=false-storage=HashSetTrace" using 1:($2/1024) title "no-instrument" with lines

unset multiplot