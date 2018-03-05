.PHONY: watch
watch:
	rlwrap lein figwheel dev


.PHONY: production
production:
	lein clean
	lein cljsbuild once min
