package com.xingguang.master.maincode.home.model;

import java.util.List;

/**
 * 创建日期：2018/6/22
 * 描述:
 * 作者:LiuYu
 */
public class TextQuestionsBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : {"IsPic":0,"Titel":"洁具产品非人为损坏质保期为10年","PIC":"","Answer":"A","SelectAnswer":"","Aata":[{"IsPic":0,"Title":"正确","Pic":null,"OptionHead":"A"},{"IsPic":0,"Title":"错误","Pic":null,"OptionHead":"B"}]}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * IsPic : 0
         * Titel : 洁具产品非人为损坏质保期为10年
         * PIC :
         * Answer : A
         * SelectAnswer :
         * Aata : [{"IsPic":0,"Title":"正确","Pic":null,"OptionHead":"A"},{"IsPic":0,"Title":"错误","Pic":null,"OptionHead":"B"}]
         */

        private int IsPic;
        private String Titel;
        private String PIC;
        private String Answer;
        private String SelectAnswer;
        private List<AataBean> Aata;

        public int getIsPic() {
            return IsPic;
        }

        public void setIsPic(int IsPic) {
            this.IsPic = IsPic;
        }

        public String getTitel() {
            return Titel;
        }

        public void setTitel(String Titel) {
            this.Titel = Titel;
        }

        public String getPIC() {
            return PIC;
        }

        public void setPIC(String PIC) {
            this.PIC = PIC;
        }

        public String getAnswer() {
            return Answer;
        }

        public void setAnswer(String Answer) {
            this.Answer = Answer;
        }

        public String getSelectAnswer() {
            return SelectAnswer;
        }

        public void setSelectAnswer(String SelectAnswer) {
            this.SelectAnswer = SelectAnswer;
        }

        public List<AataBean> getAata() {
            return Aata;
        }

        public void setAata(List<AataBean> Aata) {
            this.Aata = Aata;
        }

        public static class AataBean {
            /**
             * IsPic : 0
             * Title : 正确
             * Pic : null
             * OptionHead : A
             */

            private int IsPic;
            private String Title;
            private Object Pic;
            private String OptionHead;

            public int getIsPic() {
                return IsPic;
            }

            public void setIsPic(int IsPic) {
                this.IsPic = IsPic;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public Object getPic() {
                return Pic;
            }

            public void setPic(Object Pic) {
                this.Pic = Pic;
            }

            public String getOptionHead() {
                return OptionHead;
            }

            public void setOptionHead(String OptionHead) {
                this.OptionHead = OptionHead;
            }
        }
    }
}
