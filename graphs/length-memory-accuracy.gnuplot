set title "all-dependent"
set terminal x11
set xlabel "length"
set ylabel "memory usage"
set y2label "dependencies"
set ytics nomirror
set y2tics
set grid
plot "../formatted-results/test" using 1:2 axes x1y1 title "memory" with lines, \
"../formatted-results/test" using 1:3 axes x1y2 title "dependencies" with lines