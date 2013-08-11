set terminal pdf
set output 'dissertation-gnuplottex-fig1.pdf'
set ylabel 'bit vector length'
set xlabel 'expected insertions'
set xr [1:10000000]
set grid

# Line style for grid
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey

set xtics nomirror
set ytics nomirror

set key top left

f(n, p) = -n * log(p) / (log(2) / log(2))

plot f(x, 0.3) title 'p = 0.3' lw 3,\
 f(x, 0.03) title 'p = 0.03' lw 3,\
 f(x, 0.003) title 'p = 0.003' lw 3,\
 f(x, 0.0003) title 'p = 0.0003' lw 3
