set terminal pdf
set output 'dissertation-gnuplottex-fig1.pdf'
set multiplot layout 1, 2

load '../charts/none-dependent_length-deps_HashSetTrace-with-instrument.gnuplot'
load '../charts/none-dependent_size-deps_BloomFilterTrace-with-instrument.gnuplot'

unset multiplot
