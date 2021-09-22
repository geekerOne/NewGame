		Intent intent = new Intent();
		intent.setPackage("org.ppsspp.ppssppgold");
		intent.setClassName("org.ppsspp.ppssppgold", "org.ppsspp.ppsspp.PpssppActivity");
		intent.setData(Uri.fromFile(file));
		startActivity(intent);
