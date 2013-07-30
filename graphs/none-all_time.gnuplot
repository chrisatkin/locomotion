set multiplot layout 1,2 title "execution time"

set title "with instrument"
set xlabel "length"
set ylabel "execution time (ns)"
set grid
plot	"< sort -n ../formatted-results/all-dependent-time-instrumentation=true" using 1:2 title "all" with lines,\
	"< sort -n ../formatted-results/none-dependent-time-instrumentation=true" using 1:2 title "none" with lines,\
	

set title "no instrument"
plot	"< sort -n ../formatted-results/all-dependent-time-instrumentation=false" using 1:2 title "all" with lines,\
	"< sort -n ../formatted-results/none-dependent-time-instrumentation=false" using 1:2 title "none" with lines

unset multiplot