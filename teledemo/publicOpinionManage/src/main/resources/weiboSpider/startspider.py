from scrapy.cmdline import execute
import sys
import os

os.chdir(os.path.dirname(os.path.abspath(__file__)))
print("参数为：",str(sys.argv))
param="keyword="+str(sys.argv[1])
execute(['scrapy','crawl','example','-a',param])
#实现了传参