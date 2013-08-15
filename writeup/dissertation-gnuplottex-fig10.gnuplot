set terminal pdf
set output 'dissertation-gnuplottex-fig10.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/optimal-all-computation/time-bloom.gnuplot'

load '../dynamic/formatted-results/optimal-all-computation/time-noinstr.gnuplot'
unset multiplot
