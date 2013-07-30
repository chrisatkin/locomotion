set multiplot layout 2,2 title "dependencies detected"

unset title
set xlabel "length (x10^3)"
set ylabel "dependencies (x10^3)"
unset y2label
set grid
plot "< sort -n ../formatted-results/all-dependent-memory-instrumentation=true" using ($1/1000):($3/1000) title "all with-instrument" with lines

plot "< sort -n ../formatted-results/all-dependent-memory-instrumentation=false" using ($1/1000):($3/1000) title "all no-instrument" with lines

plot "< sort -n ../formatted-results/none-dependent-memory-instrumentation=true" using ($1/1000):($3/1000) title "none with-instrument" with lines

plot "< sort -n ../formatted-results/none-dependent-memory-instrumentation=false" using ($1/1000):($3/1000) title "none no-instrument" with lines

unset multiplot