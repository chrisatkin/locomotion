set terminal pdf
set output 'dissertation-gnuplottex-fig2.pdf'
set multiplot layout 2,1
load '../dynamic/formatted-results/implementation/insert-local.gnuplot'
load '../dynamic/formatted-results/implementation/contains-local.gnuplot'
unset multiplot
