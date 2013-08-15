set title "insertation overhead"
set ylabel "time (us)"
set xlabel "insertions (thousands)"

set logscale y

set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81
set border 3 back linestyle 80
set xtics nomirror
set ytics nomirror

set grid

plot "../dynamic/formatted-results/implementation-local/insert-results-local" using ($1/1000):2 with linespoints title "hash set, size= n" lw 1,\
	"../dynamic/formatted-results/implementation-local/insert-results-local" using ($1/1000):4 with linespoints title "guava, fpp = 0.07" lw 1,\
	"../dynamic/formatted-results/implementation-local/insert-results-local" using ($1/1000):6 with linespoints title "guava, fpp = 0.03" lw 1,\
	"../dynamic/formatted-results/implementation-local/insert-results-local" using ($1/1000):5 with linespoints title "cassandra" lw 1