(defun fibo '(n) '(if (< n 3) 1 (+ (fibo (- n 1)) (fibo (- n 2)))))
(print (fibo 36))

