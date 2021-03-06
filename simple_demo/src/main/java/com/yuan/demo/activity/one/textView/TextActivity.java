package com.yuan.demo.activity.one.textView;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ListView;

import com.yuan.basemodule.common.view.CollapsedTextView;
import com.yuan.basemodule.common.adapter.BaseListAdapter;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.demo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TextActivity extends MVPActivity implements ISwipeBack {

    @BindView(R.id.listView)
    ListView listView;
    private List<String> data;


    @Override
    protected void initData(Bundle savedInstanceState) {
        getTitleBar().setLeftIcon(R.drawable.ic_base_back_white)
                .setToolbar("折叠TextView");
        listView.setAdapter(new BaseListAdapter<String>(getData(), R.layout.text_item_layout) {
            SparseBooleanArray collapsedStatus = new SparseBooleanArray();

            @Override
            public void bindView(ViewHolder holder, String obj) {
                CollapsedTextView textView = holder.getView(R.id.ctv_content);
                textView.setTextList(obj, collapsedStatus, holder.getItemPosition());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_simple_text;
    }

    public List<String> getData() {
        if (data == null) {
            data = new ArrayList<>();
            data.add("一场细雨纷纷而至，季节的界限不在那么的明朗，走向花季的烂漫，走近雨季的清凉，五月的花朵不再像三月繁花的娇羞，仅仅吐露含苞的花蕊，透出点点的淡香， 而是倾尽全身的力气，开出沁人的心香。捻一丝细雨，听一曲轻音，让心在曼妙的乐曲中寻得安宁。推开忧伤，握住幸福，带着芳草的绿意，剪一段时光的缩影，在 人来人往中静观潮起潮落，淡看云卷云缩。");
            data.add("每一次流泪，每一次纪录，每一枚印迹，都是成长路上的必经，也是不断的在叠加生命的厚重。多彩的人生不仅是一帆风顺的泰然，更是一路走来，遭遇坎坷挫 折之时自己坚强的微笑，他人援助时留给自己窝心的感动。迎着阳光走路，身上总是感觉暖暖的，带着微笑生活，生命里也不会感觉孤单。用一种平和的心态来对待 生活，用安稳的心来平衡世间的起起落落，安然静坐，在迷乱的社会中找一席清闲之地，来安放奔波已久的心。");
            data.add("捻 一瓣花开，挽一袖清风，让幸福之花常开心间，让怡人之风卷走心霾。其实，很多时候，我们所期盼的不过是给自己一抹暖心的微笑，让幸福弥漫在心间，怀一感动 常驻心头，忧伤自然也会随之散去。心存美好，淡忘世事烦恼；静然处之 ，闲看花开花落，让风，吹走忧伤与心愁，让雨，洗尽心伤与污垢，让花，飘散阵阵芳 香，愿心，溢满感动和阳光。有梦想、有希望就有向前的勇气，有希冀的翅膀，就有翱翔蓝天的信念。");
            data.add("徜徉在笔尖下的文字里，过往的情节犹如一壶烈酒，越品越香，越饮越醉，那些拾捡不起来的人和事都将随着酒香越飘越远，直到在眼界消失殆尽。人生本就是一场 向前的旅行，谁在前进的路上多看了几道风景，谁在坎坷的途中多行了几步，这些都是活在世上的价值所在。生命，本就承载了太多的遗憾与无奈，没有必要责怪自 己太多，给心灵一丝绿意，给他人一抹微笑，无关月圆月缺，不管缘来缘去。");
            data.add("袅袅来袭的春光明媚了整个大地，缕缕怡人的春风吹开了浮在身旁的乌黑，带着春的盎然，潜伏在岁月的花丛里，赏一簇花开，看枝头摇曳吐蕊，怀着一份淡然 之心徘徊在季节的身后。学会给心灵解压，学会忘记一些烦心的事，很多时候记得的越多痛苦也就越多，丢掉那些缠绕身心的杂念，做一个简单的人，单纯的像一杯 净水，看似平淡，却是生命之源。捧着一份美好，带着一份清新，在每天太阳升起时，给他人一个微笑，给自己一个明朗的心情。");
            data.add("走 在茫茫人海，常怀一份感动来锤炼善良的内心，荡起生命之舟，扬帆在前进的旅途之中。在淡然中生活，在静默中守心，不要刻意去改变什么，走一步有一步的精 彩，停一步有一步的风景，只要心中怀着美好，哪里的风景都是倾心的。静静地把时光捧在手心，记载岁月走过残留下的痕迹。本着对生命的尊重，感谢生命中所有 的赠予，无论世事如何改变，守着那些美好的，温馨的东西，过此一生。如此，安好。");
            data.add("一场细雨纷纷而至，季节的界限不在那么的明朗，走向花季的烂漫，走近雨季的清凉，五月的花朵不再像三月繁花的娇羞，仅仅吐露含苞的花蕊，透出点点的淡香， 而是倾尽全身的力气，开出沁人的心香。捻一丝细雨，听一曲轻音，让心在曼妙的乐曲中寻得安宁。推开忧伤，握住幸福，带着芳草的绿意，剪一段时光的缩影，在 人来人往中静观潮起潮落，淡看云卷云缩。");
            data.add("每一次流泪，每一次纪录，每一枚印迹，都是成长路上的必经，也是不断的在叠加生命的厚重。多彩的人生不仅是一帆风顺的泰然，更是一路走来，遭遇坎坷挫 折之时自己坚强的微笑，他人援助时留给自己窝心的感动。迎着阳光走路，身上总是感觉暖暖的，带着微笑生活，生命里也不会感觉孤单。用一种平和的心态来对待 生活，用安稳的心来平衡世间的起起落落，安然静坐，在迷乱的社会中找一席清闲之地，来安放奔波已久的心。");
            data.add("捻 一瓣花开，挽一袖清风，让幸福之花常开心间，让怡人之风卷走心霾。其实，很多时候，我们所期盼的不过是给自己一抹暖心的微笑，让幸福弥漫在心间，怀一感动 常驻心头，忧伤自然也会随之散去。心存美好，淡忘世事烦恼；静然处之 ，闲看花开花落，让风，吹走忧伤与心愁，让雨，洗尽心伤与污垢，让花，飘散阵阵芳 香，愿心，溢满感动和阳光。有梦想、有希望就有向前的勇气，有希冀的翅膀，就有翱翔蓝天的信念。");
            data.add("徜徉在笔尖下的文字里，过往的情节犹如一壶烈酒，越品越香，越饮越醉，那些拾捡不起来的人和事都将随着酒香越飘越远，直到在眼界消失殆尽。人生本就是一场 向前的旅行，谁在前进的路上多看了几道风景，谁在坎坷的途中多行了几步，这些都是活在世上的价值所在。生命，本就承载了太多的遗憾与无奈，没有必要责怪自 己太多，给心灵一丝绿意，给他人一抹微笑，无关月圆月缺，不管缘来缘去。");
            data.add("袅袅来袭的春光明媚了整个大地，缕缕怡人的春风吹开了浮在身旁的乌黑，带着春的盎然，潜伏在岁月的花丛里，赏一簇花开，看枝头摇曳吐蕊，怀着一份淡然 之心徘徊在季节的身后。学会给心灵解压，学会忘记一些烦心的事，很多时候记得的越多痛苦也就越多，丢掉那些缠绕身心的杂念，做一个简单的人，单纯的像一杯 净水，看似平淡，却是生命之源。捧着一份美好，带着一份清新，在每天太阳升起时，给他人一个微笑，给自己一个明朗的心情。");
            data.add("走 在茫茫人海，常怀一份感动来锤炼善良的内心，荡起生命之舟，扬帆在前进的旅途之中。在淡然中生活，在静默中守心，不要刻意去改变什么，走一步有一步的精 彩，停一步有一步的风景，只要心中怀着美好，哪里的风景都是倾心的。静静地把时光捧在手心，记载岁月走过残留下的痕迹。本着对生命的尊重，感谢生命中所有 的赠予，无论世事如何改变，守着那些美好的，温馨的东西，过此一生。如此，安好。");
            data.add("一场细雨纷纷而至，季节的界限不在那么的明朗，走向花季的烂漫，走近雨季的清凉，五月的花朵不再像三月繁花的娇羞，仅仅吐露含苞的花蕊，透出点点的淡香， 而是倾尽全身的力气，开出沁人的心香。捻一丝细雨，听一曲轻音，让心在曼妙的乐曲中寻得安宁。推开忧伤，握住幸福，带着芳草的绿意，剪一段时光的缩影，在 人来人往中静观潮起潮落，淡看云卷云缩。");
            data.add("每一次流泪，每一次纪录，每一枚印迹，都是成长路上的必经，也是不断的在叠加生命的厚重。多彩的人生不仅是一帆风顺的泰然，更是一路走来，遭遇坎坷挫 折之时自己坚强的微笑，他人援助时留给自己窝心的感动。迎着阳光走路，身上总是感觉暖暖的，带着微笑生活，生命里也不会感觉孤单。用一种平和的心态来对待 生活，用安稳的心来平衡世间的起起落落，安然静坐，在迷乱的社会中找一席清闲之地，来安放奔波已久的心。");
            data.add("捻 一瓣花开，挽一袖清风，让幸福之花常开心间，让怡人之风卷走心霾。其实，很多时候，我们所期盼的不过是给自己一抹暖心的微笑，让幸福弥漫在心间，怀一感动 常驻心头，忧伤自然也会随之散去。心存美好，淡忘世事烦恼；静然处之 ，闲看花开花落，让风，吹走忧伤与心愁，让雨，洗尽心伤与污垢，让花，飘散阵阵芳 香，愿心，溢满感动和阳光。有梦想、有希望就有向前的勇气，有希冀的翅膀，就有翱翔蓝天的信念。");
            data.add("徜徉在笔尖下的文字里，过往的情节犹如一壶烈酒，越品越香，越饮越醉，那些拾捡不起来的人和事都将随着酒香越飘越远，直到在眼界消失殆尽。人生本就是一场 向前的旅行，谁在前进的路上多看了几道风景，谁在坎坷的途中多行了几步，这些都是活在世上的价值所在。生命，本就承载了太多的遗憾与无奈，没有必要责怪自 己太多，给心灵一丝绿意，给他人一抹微笑，无关月圆月缺，不管缘来缘去。");
            data.add("袅袅来袭的春光明媚了整个大地，缕缕怡人的春风吹开了浮在身旁的乌黑，带着春的盎然，潜伏在岁月的花丛里，赏一簇花开，看枝头摇曳吐蕊，怀着一份淡然 之心徘徊在季节的身后。学会给心灵解压，学会忘记一些烦心的事，很多时候记得的越多痛苦也就越多，丢掉那些缠绕身心的杂念，做一个简单的人，单纯的像一杯 净水，看似平淡，却是生命之源。捧着一份美好，带着一份清新，在每天太阳升起时，给他人一个微笑，给自己一个明朗的心情。");
            data.add("走 在茫茫人海，常怀一份感动来锤炼善良的内心，荡起生命之舟，扬帆在前进的旅途之中。在淡然中生活，在静默中守心，不要刻意去改变什么，走一步有一步的精 彩，停一步有一步的风景，只要心中怀着美好，哪里的风景都是倾心的。静静地把时光捧在手心，记载岁月走过残留下的痕迹。本着对生命的尊重，感谢生命中所有 的赠予，无论世事如何改变，守着那些美好的，温馨的东西，过此一生。如此，安好。");
            data.add("一场细雨纷纷而至，季节的界限不在那么的明朗，走向花季的烂漫，走近雨季的清凉，五月的花朵不再像三月繁花的娇羞，仅仅吐露含苞的花蕊，透出点点的淡香， 而是倾尽全身的力气，开出沁人的心香。捻一丝细雨，听一曲轻音，让心在曼妙的乐曲中寻得安宁。推开忧伤，握住幸福，带着芳草的绿意，剪一段时光的缩影，在 人来人往中静观潮起潮落，淡看云卷云缩。");
            data.add("每一次流泪，每一次纪录，每一枚印迹，都是成长路上的必经，也是不断的在叠加生命的厚重。多彩的人生不仅是一帆风顺的泰然，更是一路走来，遭遇坎坷挫 折之时自己坚强的微笑，他人援助时留给自己窝心的感动。迎着阳光走路，身上总是感觉暖暖的，带着微笑生活，生命里也不会感觉孤单。用一种平和的心态来对待 生活，用安稳的心来平衡世间的起起落落，安然静坐，在迷乱的社会中找一席清闲之地，来安放奔波已久的心。");
            data.add("捻 一瓣花开，挽一袖清风，让幸福之花常开心间，让怡人之风卷走心霾。其实，很多时候，我们所期盼的不过是给自己一抹暖心的微笑，让幸福弥漫在心间，怀一感动 常驻心头，忧伤自然也会随之散去。心存美好，淡忘世事烦恼；静然处之 ，闲看花开花落，让风，吹走忧伤与心愁，让雨，洗尽心伤与污垢，让花，飘散阵阵芳 香，愿心，溢满感动和阳光。有梦想、有希望就有向前的勇气，有希冀的翅膀，就有翱翔蓝天的信念。");
            data.add("徜徉在笔尖下的文字里，过往的情节犹如一壶烈酒，越品越香，越饮越醉，那些拾捡不起来的人和事都将随着酒香越飘越远，直到在眼界消失殆尽。人生本就是一场 向前的旅行，谁在前进的路上多看了几道风景，谁在坎坷的途中多行了几步，这些都是活在世上的价值所在。生命，本就承载了太多的遗憾与无奈，没有必要责怪自 己太多，给心灵一丝绿意，给他人一抹微笑，无关月圆月缺，不管缘来缘去。");
            data.add("袅袅来袭的春光明媚了整个大地，缕缕怡人的春风吹开了浮在身旁的乌黑，带着春的盎然，潜伏在岁月的花丛里，赏一簇花开，看枝头摇曳吐蕊，怀着一份淡然 之心徘徊在季节的身后。学会给心灵解压，学会忘记一些烦心的事，很多时候记得的越多痛苦也就越多，丢掉那些缠绕身心的杂念，做一个简单的人，单纯的像一杯 净水，看似平淡，却是生命之源。捧着一份美好，带着一份清新，在每天太阳升起时，给他人一个微笑，给自己一个明朗的心情。");
            data.add("走 在茫茫人海，常怀一份感动来锤炼善良的内心，荡起生命之舟，扬帆在前进的旅途之中。在淡然中生活，在静默中守心，不要刻意去改变什么，走一步有一步的精 彩，停一步有一步的风景，只要心中怀着美好，哪里的风景都是倾心的。静静地把时光捧在手心，记载岁月走过残留下的痕迹。本着对生命的尊重，感谢生命中所有 的赠予，无论世事如何改变，守着那些美好的，温馨的东西，过此一生。如此，安好。");
        }
        return data;
    }
}
