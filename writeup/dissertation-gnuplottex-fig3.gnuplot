set terminal pdf
set output 'dissertation-gnuplottex-fig3.pdf'
set multiplot layout 1,2
load '../charts/all-dependent_length-deps_HashSetTrace-with-instrument.gnuplot'
load '../charts/all-dependent_size-deps_BloomFilterTrace-with-instrument.gnuplot'
unset multiplot
