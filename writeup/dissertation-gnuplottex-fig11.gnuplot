set terminal pdf
set output 'dissertation-gnuplottex-fig11.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/optimal-none-computation/time-bloom.gnuplot'

load '../dynamic/formatted-results/optimal-none-computation/time-noinstr.gnuplot'
unset multiplot
