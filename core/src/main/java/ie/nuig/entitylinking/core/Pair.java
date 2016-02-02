package ie.nuig.entitylinking.core;

public class Pair<X,Y>{
		X key;
		Y value;

		public Pair(X key, Y value) {
			super();
			this.key = key;
			this.value = value;
		}

		public X getKey() {
			return key;
		}

		public Y getValue() {
			return value;
		}

	}
