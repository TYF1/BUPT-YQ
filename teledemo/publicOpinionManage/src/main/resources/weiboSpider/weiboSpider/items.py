# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class WeibospiderItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    userName=scrapy.Field()
    userTime=scrapy.Field()
    userComments=scrapy.Field()
    userFans=scrapy.Field()
    keyWord=scrapy.Field()
    Link=scrapy.Field()
    comments_1=scrapy.Field()
    pass
