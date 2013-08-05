set terminal pdf
set output 'dissertation-gnuplottex-fig6.pdf'
set multiplot layout 1,2
load '../charts/none-dependent_length-time_HashSetTrace-with-instrument.gnuplot'
load '../charts/none-dependent_size-time_BloomFilterTrace-with-instrument.gnuplot'
unset multiplot
