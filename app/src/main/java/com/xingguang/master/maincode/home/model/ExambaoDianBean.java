package com.xingguang.master.maincode.home.model;

/**
 * 创建日期：2018/6/21
 * 描述:考试入口
 * 作者:LiuYu
 */
public class ExambaoDianBean {
    /**
     * status : 1
     * msg : SUCCESS
     * data : {"DepartmentID":1,"ProfessionID":12,"QuestionBank":"安监局-安监局技工","SubjectAmount":"3","ExamDuration":"60","QualifiedStandard":"120","PracticeExplain":"模拟说明模拟说明模拟说明模拟说明模拟说明&#160;&#160;&#160;模拟说明","ExamExplain":"考试说明考试说明考试说明考试说明&#160;&#160;考试说明考试说明考试说明"}
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
         * DepartmentID : 1
         * ProfessionID : 12
         * QuestionBank : 安监局-安监局技工
         * SubjectAmount : 3
         * ExamDuration : 60
         * QualifiedStandard : 120
         * PracticeExplain : 模拟说明模拟说明模拟说明模拟说明模拟说明&#160;&#160;&#160;模拟说明
         * ExamExplain : 考试说明考试说明考试说明考试说明&#160;&#160;考试说明考试说明考试说明
         */

        private int DepartmentID;
        private int ProfessionID;
        private String QuestionBank;
        private String SubjectAmount;
        private String ExamDuration;
        private String QualifiedStandard;
        private String PracticeExplain;
        private String ExamExplain;

        public int getDepartmentID() {
            return DepartmentID;
        }

        public void setDepartmentID(int DepartmentID) {
            this.DepartmentID = DepartmentID;
        }

        public int getProfessionID() {
            return ProfessionID;
        }

        public void setProfessionID(int ProfessionID) {
            this.ProfessionID = ProfessionID;
        }

        public String getQuestionBank() {
            return QuestionBank;
        }

        public void setQuestionBank(String QuestionBank) {
            this.QuestionBank = QuestionBank;
        }

        public String getSubjectAmount() {
            return SubjectAmount;
        }

        public void setSubjectAmount(String SubjectAmount) {
            this.SubjectAmount = SubjectAmount;
        }

        public String getExamDuration() {
            return ExamDuration;
        }

        public void setExamDuration(String ExamDuration) {
            this.ExamDuration = ExamDuration;
        }

        public String getQualifiedStandard() {
            return QualifiedStandard;
        }

        public void setQualifiedStandard(String QualifiedStandard) {
            this.QualifiedStandard = QualifiedStandard;
        }

        public String getPracticeExplain() {
            return PracticeExplain;
        }

        public void setPracticeExplain(String PracticeExplain) {
            this.PracticeExplain = PracticeExplain;
        }

        public String getExamExplain() {
            return ExamExplain;
        }

        public void setExamExplain(String ExamExplain) {
            this.ExamExplain = ExamExplain;
        }
    }
}
