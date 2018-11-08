# CommonItemDecoration
一些常见的通用ItemDecoration

## FirstItemTopDivider
给首个Item的上方添加一条分割线，让下拉时可以看起来更美观。
用法：
```
FirstItemTopDivider.of(margin) 或
FirstItemTopDivider.of(marginLeft, marginRight) 或
FirstItemTopDivider.of(dividerDrawable, margin) 或
FirstItemTopDivider.of(dividerDrawable, marginLeft, marginRight)
```
效果：
![不加FirstItemTopDivider效果](https://i.imgur.com/VONxOk8.png)  ![加上FirstItemTopDivider效果](https://i.imgur.com/rRcqL9Z.png)


## GridSpaceDecoration
为item添加网格透明间隔, 用法：
```
new GridItemDecoration(spacingPx) 或
GridItemDecoration(spacingDimen, columns)
```
效果：
![](https://i.imgur.com/YopPZvq.png)


## LinearItemDecoration
为线性布局item的底部（垂直布局）或右边（水平布局）添加分割线
用法及效果：
+ 普通灰色1px分割线
```
ItemDecorationUtil.normalDivider(recyclerView)：
```
![](https://i.imgur.com/alSmDsw.png)

+ 带margin的普通分割线
```
ItemDecorationUtil.normalDivider(recyclerView, margin) 或 
ItemDecorationUtil.normalDivider(recyclerView, marginLeft, marginRight)：
```
![](https://i.imgur.com/d6oOSwI.png)

+ 忽略最后一个item的普通分割线
```
ItemDecorationUtil.dividerAndSkipLast(recyclerView)：
```
![](https://i.imgur.com/zsuH15Q.png)

+ 从指定位置开始画的普通分割线
```
ItemDecorationUtil.dividerWithStartPosition(recyclerView, startPosition)：
```
![](https://i.imgur.com/CYlrvS2.png)

+ 自定义分割线
```
ItemDecorationUtil.customDivider(recyclerView, drawableId)：
```
![](https://i.imgur.com/xmc1akS.png)

当然也可以直接用`new LinearItemDecoration.Builder`的方式进行创建。

## SpecifiedDirectionSpaceDecoration
为item的某个方向添加空白间隔，并支持指定某些特定条件下是否需要decoration，如为每个item的上下左右或垂直或水平某个方向添加decoration，或者为偶数项添加decoration等特殊条件。
用法及效果：
+ 为每个item底部加一段默认的透明间隔（10dp）
```
ItemDecorationUtil.bottomSpaceDecoration(recyclerView, space) 或
ItemDecorationUtil.bottomSpaceDecoration(recyclerView, space, decorationPositionListener)
```
![](https://i.imgur.com/2YbkFNO.png)

+ 给除了最后一个item的其他item底部添加一段默认的透明间隔（10dp）。因为在LoadMoreRecyclerView中，item数量超过一页且到底时会默认为最后一个item添加10dp的间隔，所以这里需要忽略最后一个，避免重复添加。
```
ItemDecorationUtil.bottomSpaceAndSkipLast(recyclerView, space)
```
![](https://i.imgur.com/XO2naAt.png)

同理，其他方向都可以通过`ItemDecorationUtil`找到。
**Tip**: 在线性布局下使用`LinearPowerfulSpacesDecoration`会更加灵活。

## LinearPowerfulSpacesDecoration
用于线性布局的多功能透明间隔ItemDecoration：
1. 给item添加透明间隔
2. 支持在透明间隔的顶部和底部分别添加分割线，即分割线-间隔-分割线
3. 支持自定义分割线样式
4. 支持指定某些特定条件下是否需要decoration，在线性布局下，可以实现`SpecifiedDirectionSpaceDecoration`所有功能，且更加灵活
5. 可以通过控制边缘变量决定列表边缘是否需要间隔

用法及效果：
+ 不带分割线的透明间隔
```
ItemDecorationUtil.spaceDecoration(recyclerView, space)
```
![](https://i.imgur.com/VE55DCX.png)

+ 带分割线的透明间隔
第一个item若是需要画顶部的decoration, 则只会画该顶部间隔的下分割线，最后一个item若是需要画底部decoration，则只会画该底部间隔的上分割线。
```
ItemDecorationUtil.spaceDecorationWith2Divider(recyclerView, space, isIgnoreLastBottom)
```
![](https://i.imgur.com/ai7lRg9.png)     ![](https://i.imgur.com/T6k97B3.png)

更多复杂的用法可以通过`ItemDecorationUtil`或`LinearPowerfulSpacesDecoration.Builder`实现，特别是对于特殊条件下的item，可以通过`LinearPowerfulSpacesDecoration.DecorationPositionListener`进行控制。

## SimpleStickyDecoration
为同一组别的item添加组别名
用法：
```
new SimpleStickyDecoration.Builder(groupListener).build()
```
效果：
![](https://i.imgur.com/0r3qkXl.png)