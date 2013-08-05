set terminal pdf
set output 'dissertation-gnuplottex-fig2.pdf'
set multiplot layout 1,2
load '../charts/none-dependent_length-memory_HashSetTrace-with-instrument.gnuplot'
load '../charts/none-dependent_size-memory_BloomFilterTrace-with-instrument.gnuplot'
unset multiplot
