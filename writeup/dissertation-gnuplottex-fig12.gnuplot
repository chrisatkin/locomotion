set terminal pdf
set output 'dissertation-gnuplottex-fig12.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/premature-all-computation/time-bloom.gnuplot'
load '../dynamic/formatted-results/premature-all-computation/time-hash.gnuplot'
unset multiplot
