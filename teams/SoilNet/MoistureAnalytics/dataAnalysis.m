    clear;
    m = 0;
    figure;
    xlabel('minutes');
    ylabel('Moisture Value');
    hold on;
while(1)
    clear A;
    clear sen1;
    clear sen2;
    clear t1;
    clear t2;
  
    A = importdata('sensordata.txt', ',');
    n = length(A(:,1));
    sx1 = 0;
    sx2 = 0;
    bv = A(1,5)*60 + A(1,6) + A(1,7)/60;
    
    for i=1:n
        if(A(i,1) == 1)
            sx1 = sx1 + 1;
            sen1(sx1) = A(i,2);
            t1(sx1) = (A(i,5)*60 + A(i,6) + A(i,7)/60) - bv;
        else 
            sx2 = sx2+1;
            sen2(sx2) = A(i,2);
            t2(sx2) = A(i,5)*60 + A(i,6) + A(i,7)/60 - bv;
        end
    end
    
    m = m + 1;
    disp(m);
  
    if(m ~= 1)
        delete(h1);
        delete(h2);
        delete(g);
    end
    
     h1 = plot(t1,sen1,'-k');
     h2 = plot(t2,sen2, '-r');
     str = [ 'time = ' num2str(A(i,5)) ':' num2str(A(i,6)) ':' num2str(A(i,7)) ];
     g = legend(str);   
     pause(1);
end
 
hold off;