package model.rules;

public enum Rules {
    NOTNULL {
        public <T> boolean evaluate(T x) {
            return x != null;
        }
    },
    LESSTHAN {
        public <T> boolean evaluate(T x, LessThan rule) {
            return (long) x < rule.value();
        }
    },
    MORETHAN {
        public <T> boolean evaluate(T x, MoreThan rule) {
            return (long) x > rule.value();
        }
    };


}
