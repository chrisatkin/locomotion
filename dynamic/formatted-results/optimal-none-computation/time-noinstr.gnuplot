set title "no instrumentation"
set xlabel "accesses (thousands)"
set ylabel "execution time (seconds)"

#set logscale y

# grid style
set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81
set border 3 back linestyle 80
set xtics nomirror
set ytics nomirror

factor(x) = x

set key top left

plot "< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 10000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 10000" ls 1 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 20000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 20000" ls 2 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 30000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 30000" ls 3 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 40000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 40000" ls 4 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 50000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 50000" ls 5 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 60000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 60000" ls 6 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 70000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 70000" ls 7 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 80000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 80000" ls 8 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 90000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 90000" ls 9 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/optimal-none-computation/instrumentation=false-storage=HashSetTrace | awk '$5 == 100000'" using ($1/1000):($3/1000000000) with linespoints title "computation = 100000" ls 10 lw 4