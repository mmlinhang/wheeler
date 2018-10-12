package lh.wheel.annotationTest;

import lh.wheel.annotation.Action;
import lh.wheel.annotation.Controller;
import lh.wheel.annotation.Inject;

@Controller
public class ControllerExam {
    @Inject
    private ServiceExam serviceExam;

    /**
     * 用于测试注入时找不到 bean 的情况
     */
//    @Inject
//    private NotABean notABean;
}
