set terminal pdf
set output 'dissertation-gnuplottex-fig2.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/implementation/insert.gnuplot'
load '../dynamic/formatted-results/implementation/contains.gnuplot'
unset multiplot
