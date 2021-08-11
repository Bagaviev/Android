WITH Result AS (
	SELECT * FROM (
		SELECT *, (max_rn - row_num) st FROM
		(
			SELECT *, max(row_num) OVER (partitiON by SUBS_ID) max_rn FROM (
				SELECT *, ROW_NUMBER() OVER (PARTITION BY SUBS_ID ORDER BY BALANCE_DATE) row_num
				FROM test
			) a
		) b 
		WHERE (max_rn - row_num) <= 1
	) c
)
SELECT DISTINCT
	t1.SUBS_ID
  , t1.BALANCE_DATE as BALANCE_DATE_CURR
  , t1.BALANCE_AMT as BALANCE_AMT_CURR
  , t2.BALANCE_DATE as BALANCE_DATE_1
  , t2.BALANCE_AMT as BALANCE_AMT_1
FROM Result t1
INNER JOIN (SELECT * FROM Result WHERE st = 1) t2 ON t1.SUBS_ID = t2.SUBS_ID
WHERE t1.st = 0