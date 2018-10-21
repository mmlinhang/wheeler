package lh.wheel.annotationTest;

import lh.wheel.annotation.mvc.Action;
import lh.wheel.annotation.mvc.Controller;
import lh.wheel.annotation.mvc.Inject;
import lh.wheel.constant.RequestMethod;

@Controller
public class ControllerExam {
    @Inject
    private ServiceExam serviceExam;

    /**
     * 用于测试注入时找不到 bean 的情况
     */
//    @Inject
//    private NotABean notABean;

    @Action(method = RequestMethod.GET, url = "listStudents")
    public void listStudents() {

    }
}
