set terminal pdf
set output 'dissertation-gnuplottex-fig5.pdf'
set multiplot layout 1,2
load '../charts/all-dependent_length-time_HashSetTrace-with-instrument.gnuplot'
load '../charts/all-dependent_size-time_BloomFilterTrace-with-instrument.gnuplot'
unset multiplot
