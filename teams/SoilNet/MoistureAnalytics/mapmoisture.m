    clear;
    m = 0;
    figure;
    hold on;
    Sigma = [.25 .3; .3 1];
    x1 = 0:.05:1; x2 = 0:.05:1;
    [X1,X2] = meshgrid(x1,x2);
while(1)
    clear A;
    clear sen1;
    clear sen2;
    clear mu;
    clear F;
    
    A = importdata('sensoravg.txt', ',');
    sen1 = A(1,1);
    sen2 = A(1,2);
    
    mu = [sen1 sen2];
    F = mvnpdf([X1(:) X2(:)],[0.5 0.3],Sigma) + mvnpdf([X1(:) X2(:)],[0.1 0.2],Sigma);
    F = reshape(F,length(x2),length(x1));
    
    m = m + 1;
    disp(m);
  
    if(m ~= 1)
        delete(h);
    end
    
    
    [C,h] = contourf(X1,X2,F);
    
    
     pause(60);
end
 
hold off;