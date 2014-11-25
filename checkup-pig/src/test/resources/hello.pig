data =
    LOAD 'input'
    AS (query:CHARARRAY);

dump data;    
 
queries_group =
    GROUP data
    BY query; 

dump queries_group;    

queries_count = 
    FOREACH queries_group 
    GENERATE 
        group AS query, 
        COUNT(data) AS total;
        
queries_ordered =
    ORDER queries_count
    BY total DESC, query;
            
queries_limit =
    LIMIT queries_ordered $n;

STORE queries_limit INTO 'output';