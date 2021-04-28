package model.rules;

public enum Rules {
    NOTNULL {
        @Override
        public <T> boolean evaluate(T x, Rule rule) {
            return x != null;
        }
    },
    LESSTHAN {
        @Override
        public <T> boolean evaluate(T x, Rule rule) {
            return x <
        }
    },
    MORETHAN {
        @Override
        public <T> boolean evaluate(T x, Rule rule) {
            return false;
        }
    }

    abstract public <T> boolean evaluate(T x, Rule rule);
}
