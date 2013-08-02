set multiplot layout 2,1 title "vector addition"

set title "memory usage"
set xlabel "length"
set ylabel "memory (kb)"
set grid
plot "< sort -n ../formatted-results/vector-addition-memory-instrumentation=true" using 1:($2/1024) title "with-instrument" with lines,\
	"< sort -n ../formatted-results/vector-addition-memory-instrumentation=false" using 1:($2/1024) title "no-instrument" with lines

set title "execution time"
set xlabel "length"
set ylabel "time (ns) (log)"
set grid
set logscale y
plot "< sort -n ../formatted-results/vector-addition-time-instrumentation=true" using 1:2 title "with-instrument" with lines,\
	"< sort -n ../formatted-results/vector-addition-time-instrumentation=false" using 1:2 title "no-instrument" with lines

unset multiplot