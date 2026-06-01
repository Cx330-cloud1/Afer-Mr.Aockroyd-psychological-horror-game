package com.roger.mystery.service;

import com.roger.mystery.model.Ending;
import com.roger.mystery.model.GameState;
import org.springframework.stereotype.Service;

@Service
public class EndingService {
    public Ending determineEnding(GameState state, String finalChoiceId) {
        int suspicion = state.getPoirotSuspicion();
        int awareness = state.getSelfAwareness();
        int memories = state.getCollectedMemories().size();

        if (memories >= 8 && awareness > 90 && "choice_4_4_hidden".equals(finalChoiceId)) {
            return new Ending("ending_truth_lie", "真相与谎言", "你主动召集所有人，给出了一个完美到无人质疑的谎言。笔尖落下时，字迹逐渐像罗杰本人。最好的真相，是没人质疑的谎言。", "hidden");
        }
        if (suspicion < 30 && awareness < 30) {
            return new Ending("ending_perfect_escape", "完美逃脱", "波洛几乎指向拉尔夫。你在日记里写下正义得到伸张，但纸页上的文字渐渐扭曲：你骗过了所有人，甚至骗过了自己。", "cold");
        }
        if (suspicion > 80 && !"choice_4_4_confess".equals(finalChoiceId)) {
            return new Ending("ending_public_exposure", "公开揭露", "波洛召集所有人，逐条还原你的时间线、纸张、手套与密道。你在狱中终于写下真实日记：我是詹姆斯·谢泼德，我杀了罗杰。", "judgement");
        }
        if (suspicion > 70 && "choice_4_4_confess".equals(finalChoiceId)) {
            return new Ending("ending_poirot_mercy", "波洛的怜悯", "波洛没有立刻报警。他留下的是沉默，而沉默比手铐更重。你将用余生记住今晚。", "mercy");
        }
        return new Ending("ending_reasonable_doubt", "合理怀疑", "证据不足以定罪，案件成了悬案。波洛离开前说，有些真相会像坏死组织一样，从内部腐蚀灵魂。", "ambiguous");
    }
}
