package com.yjj.study.chain;

public abstract class LoginChain {

    protected LoginChain next;

    public LoginChain() {
    }

    public void setNext(LoginChain next){
        this.next = next;
    }

    public abstract void login(Member member);

    public static class Builder<T>{

        private LoginChain start;
        private LoginChain end;

        public Builder addValidate(LoginChain loginChain){

            if(this.start == null){
               this.start = this.end = loginChain;
            }else{
                // 这里第一次进来的时候就把新的链路放到next中，然后把引用对象改为新的链路，从而达到一直在修改新联路
                this.end.setNext(loginChain);
                this.end = loginChain;
            }
            return this;
        }

        public LoginChain builder(){
            return this.start;
        }


    }

}
