/******/ (function(modules) { // webpackBootstrap
/******/ 	// install a JSONP callback for chunk loading
/******/ 	function webpackJsonpCallback(data) {
/******/ 		var chunkIds = data[0];
/******/ 		var moreModules = data[1];
/******/ 		var executeModules = data[2];
/******/
/******/ 		// add "moreModules" to the modules object,
/******/ 		// then flag all "chunkIds" as loaded and fire callback
/******/ 		var moduleId, chunkId, i = 0, resolves = [];
/******/ 		for(;i < chunkIds.length; i++) {
/******/ 			chunkId = chunkIds[i];
/******/ 			if(Object.prototype.hasOwnProperty.call(installedChunks, chunkId) && installedChunks[chunkId]) {
/******/ 				resolves.push(installedChunks[chunkId][0]);
/******/ 			}
/******/ 			installedChunks[chunkId] = 0;
/******/ 		}
/******/ 		for(moduleId in moreModules) {
/******/ 			if(Object.prototype.hasOwnProperty.call(moreModules, moduleId)) {
/******/ 				modules[moduleId] = moreModules[moduleId];
/******/ 			}
/******/ 		}
/******/ 		if(parentJsonpFunction) parentJsonpFunction(data);
/******/
/******/ 		while(resolves.length) {
/******/ 			resolves.shift()();
/******/ 		}
/******/
/******/ 		// add entry modules from loaded chunk to deferred list
/******/ 		deferredModules.push.apply(deferredModules, executeModules || []);
/******/
/******/ 		// run deferred modules when all chunks ready
/******/ 		return checkDeferredModules();
/******/ 	};
/******/ 	function checkDeferredModules() {
/******/ 		var result;
/******/ 		for(var i = 0; i < deferredModules.length; i++) {
/******/ 			var deferredModule = deferredModules[i];
/******/ 			var fulfilled = true;
/******/ 			for(var j = 1; j < deferredModule.length; j++) {
/******/ 				var depId = deferredModule[j];
/******/ 				if(installedChunks[depId] !== 0) fulfilled = false;
/******/ 			}
/******/ 			if(fulfilled) {
/******/ 				deferredModules.splice(i--, 1);
/******/ 				result = __webpack_require__(__webpack_require__.s = deferredModule[0]);
/******/ 			}
/******/ 		}
/******/
/******/ 		return result;
/******/ 	}
/******/
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// object to store loaded and loading chunks
/******/ 	// undefined = chunk not loaded, null = chunk preloaded/prefetched
/******/ 	// Promise = chunk loading, 0 = chunk loaded
/******/ 	var installedChunks = {
/******/ 		"app": 0
/******/ 	};
/******/
/******/ 	var deferredModules = [];
/******/
/******/ 	// script path function
/******/ 	function jsonpScriptSrc(chunkId) {
/******/ 		return __webpack_require__.p + "js/" + ({}[chunkId]||chunkId) + ".js"
/******/ 	}
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/ 	// This file contains only the entry chunk.
/******/ 	// The chunk loading function for additional chunks
/******/ 	__webpack_require__.e = function requireEnsure(chunkId) {
/******/ 		var promises = [];
/******/
/******/
/******/ 		// JSONP chunk loading for javascript
/******/
/******/ 		var installedChunkData = installedChunks[chunkId];
/******/ 		if(installedChunkData !== 0) { // 0 means "already installed".
/******/
/******/ 			// a Promise means "currently loading".
/******/ 			if(installedChunkData) {
/******/ 				promises.push(installedChunkData[2]);
/******/ 			} else {
/******/ 				// setup Promise in chunk cache
/******/ 				var promise = new Promise(function(resolve, reject) {
/******/ 					installedChunkData = installedChunks[chunkId] = [resolve, reject];
/******/ 				});
/******/ 				promises.push(installedChunkData[2] = promise);
/******/
/******/ 				// start chunk loading
/******/ 				var script = document.createElement('script');
/******/ 				var onScriptComplete;
/******/
/******/ 				script.charset = 'utf-8';
/******/ 				script.timeout = 120;
/******/ 				if (__webpack_require__.nc) {
/******/ 					script.setAttribute("nonce", __webpack_require__.nc);
/******/ 				}
/******/ 				script.src = jsonpScriptSrc(chunkId);
/******/
/******/ 				// create error before stack unwound to get useful stacktrace later
/******/ 				var error = new Error();
/******/ 				onScriptComplete = function (event) {
/******/ 					// avoid mem leaks in IE.
/******/ 					script.onerror = script.onload = null;
/******/ 					clearTimeout(timeout);
/******/ 					var chunk = installedChunks[chunkId];
/******/ 					if(chunk !== 0) {
/******/ 						if(chunk) {
/******/ 							var errorType = event && (event.type === 'load' ? 'missing' : event.type);
/******/ 							var realSrc = event && event.target && event.target.src;
/******/ 							error.message = 'Loading chunk ' + chunkId + ' failed.\n(' + errorType + ': ' + realSrc + ')';
/******/ 							error.name = 'ChunkLoadError';
/******/ 							error.type = errorType;
/******/ 							error.request = realSrc;
/******/ 							chunk[1](error);
/******/ 						}
/******/ 						installedChunks[chunkId] = undefined;
/******/ 					}
/******/ 				};
/******/ 				var timeout = setTimeout(function(){
/******/ 					onScriptComplete({ type: 'timeout', target: script });
/******/ 				}, 120000);
/******/ 				script.onerror = script.onload = onScriptComplete;
/******/ 				document.head.appendChild(script);
/******/ 			}
/******/ 		}
/******/ 		return Promise.all(promises);
/******/ 	};
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/ 	// on error function for async loading
/******/ 	__webpack_require__.oe = function(err) { console.error(err); throw err; };
/******/
/******/ 	var jsonpArray = window["webpackJsonp"] = window["webpackJsonp"] || [];
/******/ 	var oldJsonpFunction = jsonpArray.push.bind(jsonpArray);
/******/ 	jsonpArray.push = webpackJsonpCallback;
/******/ 	jsonpArray = jsonpArray.slice();
/******/ 	for(var i = 0; i < jsonpArray.length; i++) webpackJsonpCallback(jsonpArray[i]);
/******/ 	var parentJsonpFunction = oldJsonpFunction;
/******/
/******/
/******/ 	// add entry module to deferred list
/******/ 	deferredModules.push([0,"chunk-vendors"]);
/******/ 	// run deferred modules when ready
/******/ 	return checkDeferredModules();
/******/ })
/************************************************************************/
/******/ ({

/***/ "./node_modules/cache-loader/dist/cjs.js?!./node_modules/babel-loader/lib/index.js!./node_modules/cache-loader/dist/cjs.js?!./node_modules/vue-loader/lib/index.js?!./src/App.vue?vue&type=script&lang=js&":
/*!*************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/cache-loader/dist/cjs.js??ref--12-0!./node_modules/babel-loader/lib!./node_modules/cache-loader/dist/cjs.js??ref--0-0!./node_modules/vue-loader/lib??vue-loader-options!./src/App.vue?vue&type=script&lang=js& ***!
  \*************************************************************************************************************************************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var core_js_modules_es_array_concat__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! core-js/modules/es.array.concat */ \"./node_modules/core-js/modules/es.array.concat.js\");\n/* harmony import */ var core_js_modules_es_array_concat__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_es_array_concat__WEBPACK_IMPORTED_MODULE_0__);\n/* harmony import */ var _app_config__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @/app.config */ \"./src/app.config.json\");\nvar _app_config__WEBPACK_IMPORTED_MODULE_1___namespace = /*#__PURE__*/__webpack_require__.t(/*! @/app.config */ \"./src/app.config.json\", 1);\n\n\n/* harmony default export */ __webpack_exports__[\"default\"] = ({\n  name: \"app\",\n  page: {\n    // All subcomponent titles will be injected into this template.\n    titleTemplate: function titleTemplate(title) {\n      title = typeof title === \"function\" ? title(this.$store) : title;\n      return title ? \"\".concat(title, \" | \").concat(_app_config__WEBPACK_IMPORTED_MODULE_1__.title) : _app_config__WEBPACK_IMPORTED_MODULE_1__.title;\n    }\n  }\n});\n\n//# sourceURL=webpack:///./src/App.vue?./node_modules/cache-loader/dist/cjs.js??ref--12-0!./node_modules/babel-loader/lib!./node_modules/cache-loader/dist/cjs.js??ref--0-0!./node_modules/vue-loader/lib??vue-loader-options");

/***/ }),

/***/ "./node_modules/cache-loader/dist/cjs.js?!./node_modules/babel-loader/lib/index.js!./node_modules/cache-loader/dist/cjs.js?!./node_modules/vue-loader/lib/index.js?!./src/router/views/utility/404.vue?vue&type=script&lang=js&":
/*!**********************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/cache-loader/dist/cjs.js??ref--12-0!./node_modules/babel-loader/lib!./node_modules/cache-loader/dist/cjs.js??ref--0-0!./node_modules/vue-loader/lib??vue-loader-options!./src/router/views/utility/404.vue?vue&type=script&lang=js& ***!
  \**********************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var core_js_modules_es_symbol__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! core-js/modules/es.symbol */ \"./node_modules/core-js/modules/es.symbol.js\");\n/* harmony import */ var core_js_modules_es_symbol__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_es_symbol__WEBPACK_IMPORTED_MODULE_0__);\n/* harmony import */ var core_js_modules_es_symbol_description__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! core-js/modules/es.symbol.description */ \"./node_modules/core-js/modules/es.symbol.description.js\");\n/* harmony import */ var core_js_modules_es_symbol_description__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_es_symbol_description__WEBPACK_IMPORTED_MODULE_1__);\n/* harmony import */ var _app_config__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @/app.config */ \"./src/app.config.json\");\nvar _app_config__WEBPACK_IMPORTED_MODULE_2___namespace = /*#__PURE__*/__webpack_require__.t(/*! @/app.config */ \"./src/app.config.json\", 1);\n\n\n\n/* harmony default export */ __webpack_exports__[\"default\"] = ({\n  page: {\n    title: \"404 Error Page\",\n    meta: [{\n      name: \"description\",\n      content: _app_config__WEBPACK_IMPORTED_MODULE_2__.description\n    }]\n  }\n});\n\n//# sourceURL=webpack:///./src/router/views/utility/404.vue?./node_modules/cache-loader/dist/cjs.js??ref--12-0!./node_modules/babel-loader/lib!./node_modules/cache-loader/dist/cjs.js??ref--0-0!./node_modules/vue-loader/lib??vue-loader-options");

/***/ }),

/***/ "./node_modules/cache-loader/dist/cjs.js?{\"cacheDirectory\":\"node_modules/.cache/vue-loader\",\"cacheIdentifier\":\"da1db418-vue-loader-template\"}!./node_modules/vue-loader/lib/loaders/templateLoader.js?!./node_modules/cache-loader/dist/cjs.js?!./node_modules/vue-loader/lib/index.js?!./src/App.vue?vue&type=template&id=7ba5bd90&":
/*!*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/cache-loader/dist/cjs.js?{"cacheDirectory":"node_modules/.cache/vue-loader","cacheIdentifier":"da1db418-vue-loader-template"}!./node_modules/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/cache-loader/dist/cjs.js??ref--0-0!./node_modules/vue-loader/lib??vue-loader-options!./src/App.vue?vue&type=template&id=7ba5bd90& ***!
  \*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"render\", function() { return render; });\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"staticRenderFns\", function() { return staticRenderFns; });\nvar render = function() {\n  var _vm = this\n  var _h = _vm.$createElement\n  var _c = _vm._self._c || _h\n  return _c(\"div\", { attrs: { id: \"app\" } }, [_c(\"RouterView\")], 1)\n}\nvar staticRenderFns = []\nrender._withStripped = true\n\n\n\n//# sourceURL=webpack:///./src/App.vue?./node_modules/cache-loader/dist/cjs.js?%7B%22cacheDirectory%22:%22node_modules/.cache/vue-loader%22,%22cacheIdentifier%22:%22da1db418-vue-loader-template%22%7D!./node_modules/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/cache-loader/dist/cjs.js??ref--0-0!./node_modules/vue-loader/lib??vue-loader-options");

/***/ }),

/***/ "./node_modules/cache-loader/dist/cjs.js?{\"cacheDirectory\":\"node_modules/.cache/vue-loader\",\"cacheIdentifier\":\"da1db418-vue-loader-template\"}!./node_modules/vue-loader/lib/loaders/templateLoader.js?!./node_modules/cache-loader/dist/cjs.js?!./node_modules/vue-loader/lib/index.js?!./src/router/views/utility/404.vue?vue&type=template&id=7c5eae87&":
/*!******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/cache-loader/dist/cjs.js?{"cacheDirectory":"node_modules/.cache/vue-loader","cacheIdentifier":"da1db418-vue-loader-template"}!./node_modules/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/cache-loader/dist/cjs.js??ref--0-0!./node_modules/vue-loader/lib??vue-loader-options!./src/router/views/utility/404.vue?vue&type=template&id=7c5eae87& ***!
  \******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"render\", function() { return render; });\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"staticRenderFns\", function() { return staticRenderFns; });\nvar render = function() {\n  var _vm = this\n  var _h = _vm.$createElement\n  var _c = _vm._self._c || _h\n  return _c(\"div\", { staticClass: \"account-pages my-5 pt-5\" }, [\n    _c(\"div\", { staticClass: \"container\" }, [\n      _c(\"div\", { staticClass: \"row\" }, [\n        _c(\"div\", { staticClass: \"col-lg-12\" }, [\n          _c(\"div\", { staticClass: \"text-center mb-5\" }, [\n            _vm._m(0),\n            _c(\"h4\", { staticClass: \"text-uppercase\" }, [\n              _vm._v(\"Sorry, page not found\")\n            ]),\n            _c(\n              \"div\",\n              { staticClass: \"mt-5 text-center\" },\n              [\n                _c(\n                  \"router-link\",\n                  {\n                    staticClass: \"btn btn-primary\",\n                    attrs: { tag: \"a\", to: \"/\" }\n                  },\n                  [_vm._v(\"Back to Dashboard\")]\n                )\n              ],\n              1\n            )\n          ])\n        ])\n      ]),\n      _vm._m(1)\n    ])\n  ])\n}\nvar staticRenderFns = [\n  function() {\n    var _vm = this\n    var _h = _vm.$createElement\n    var _c = _vm._self._c || _h\n    return _c(\"h1\", { staticClass: \"display-2 font-weight-medium\" }, [\n      _vm._v(\" 4\"),\n      _c(\"i\", { staticClass: \"bx bx-buoy bx-spin text-primary display-3\" }),\n      _vm._v(\"4 \")\n    ])\n  },\n  function() {\n    var _vm = this\n    var _h = _vm.$createElement\n    var _c = _vm._self._c || _h\n    return _c(\"div\", { staticClass: \"row justify-content-center\" }, [\n      _c(\"div\", { staticClass: \"col-md-8 col-xl-6\" }, [\n        _c(\"div\", [\n          _c(\"img\", {\n            staticClass: \"img-fluid\",\n            attrs: { src: __webpack_require__(/*! @/assets/images/error-img.png */ \"./src/assets/images/error-img.png\"), alt: \"\" }\n          })\n        ])\n      ])\n    ])\n  }\n]\nrender._withStripped = true\n\n\n\n//# sourceURL=webpack:///./src/router/views/utility/404.vue?./node_modules/cache-loader/dist/cjs.js?%7B%22cacheDirectory%22:%22node_modules/.cache/vue-loader%22,%22cacheIdentifier%22:%22da1db418-vue-loader-template%22%7D!./node_modules/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/cache-loader/dist/cjs.js??ref--0-0!./node_modules/vue-loader/lib??vue-loader-options");

/***/ }),

/***/ "./src/App.vue":
/*!*********************!*\
  !*** ./src/App.vue ***!
  \*********************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _App_vue_vue_type_template_id_7ba5bd90___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./App.vue?vue&type=template&id=7ba5bd90& */ \"./src/App.vue?vue&type=template&id=7ba5bd90&\");\n/* harmony import */ var _App_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./App.vue?vue&type=script&lang=js& */ \"./src/App.vue?vue&type=script&lang=js&\");\n/* empty/unused harmony star reexport *//* harmony import */ var _node_modules_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../node_modules/vue-loader/lib/runtime/componentNormalizer.js */ \"./node_modules/vue-loader/lib/runtime/componentNormalizer.js\");\n\n\n\n\n\n/* normalize component */\n\nvar component = Object(_node_modules_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_2__[\"default\"])(\n  _App_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[\"default\"],\n  _App_vue_vue_type_template_id_7ba5bd90___WEBPACK_IMPORTED_MODULE_0__[\"render\"],\n  _App_vue_vue_type_template_id_7ba5bd90___WEBPACK_IMPORTED_MODULE_0__[\"staticRenderFns\"],\n  false,\n  null,\n  null,\n  null\n  \n)\n\n/* hot reload */\nif (false) { var api; }\ncomponent.options.__file = \"src/App.vue\"\n/* harmony default export */ __webpack_exports__[\"default\"] = (component.exports);\n\n//# sourceURL=webpack:///./src/App.vue?");

/***/ }),

/***/ "./src/App.vue?vue&type=script&lang=js&":
/*!**********************************************!*\
  !*** ./src/App.vue?vue&type=script&lang=js& ***!
  \**********************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _node_modules_cache_loader_dist_cjs_js_ref_12_0_node_modules_babel_loader_lib_index_js_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_App_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../node_modules/cache-loader/dist/cjs.js??ref--12-0!../node_modules/babel-loader/lib!../node_modules/cache-loader/dist/cjs.js??ref--0-0!../node_modules/vue-loader/lib??vue-loader-options!./App.vue?vue&type=script&lang=js& */ \"./node_modules/cache-loader/dist/cjs.js?!./node_modules/babel-loader/lib/index.js!./node_modules/cache-loader/dist/cjs.js?!./node_modules/vue-loader/lib/index.js?!./src/App.vue?vue&type=script&lang=js&\");\n/* empty/unused harmony star reexport */ /* harmony default export */ __webpack_exports__[\"default\"] = (_node_modules_cache_loader_dist_cjs_js_ref_12_0_node_modules_babel_loader_lib_index_js_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_App_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[\"default\"]); \n\n//# sourceURL=webpack:///./src/App.vue?");

/***/ }),

/***/ "./src/App.vue?vue&type=template&id=7ba5bd90&":
/*!****************************************************!*\
  !*** ./src/App.vue?vue&type=template&id=7ba5bd90& ***!
  \****************************************************/
/*! exports provided: render, staticRenderFns */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _node_modules_cache_loader_dist_cjs_js_cacheDirectory_node_modules_cache_vue_loader_cacheIdentifier_da1db418_vue_loader_template_node_modules_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_App_vue_vue_type_template_id_7ba5bd90___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../node_modules/cache-loader/dist/cjs.js?{\"cacheDirectory\":\"node_modules/.cache/vue-loader\",\"cacheIdentifier\":\"da1db418-vue-loader-template\"}!../node_modules/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!../node_modules/cache-loader/dist/cjs.js??ref--0-0!../node_modules/vue-loader/lib??vue-loader-options!./App.vue?vue&type=template&id=7ba5bd90& */ \"./node_modules/cache-loader/dist/cjs.js?{\\\"cacheDirectory\\\":\\\"node_modules/.cache/vue-loader\\\",\\\"cacheIdentifier\\\":\\\"da1db418-vue-loader-template\\\"}!./node_modules/vue-loader/lib/loaders/templateLoader.js?!./node_modules/cache-loader/dist/cjs.js?!./node_modules/vue-loader/lib/index.js?!./src/App.vue?vue&type=template&id=7ba5bd90&\");\n/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, \"render\", function() { return _node_modules_cache_loader_dist_cjs_js_cacheDirectory_node_modules_cache_vue_loader_cacheIdentifier_da1db418_vue_loader_template_node_modules_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_App_vue_vue_type_template_id_7ba5bd90___WEBPACK_IMPORTED_MODULE_0__[\"render\"]; });\n\n/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, \"staticRenderFns\", function() { return _node_modules_cache_loader_dist_cjs_js_cacheDirectory_node_modules_cache_vue_loader_cacheIdentifier_da1db418_vue_loader_template_node_modules_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_App_vue_vue_type_template_id_7ba5bd90___WEBPACK_IMPORTED_MODULE_0__[\"staticRenderFns\"]; });\n\n\n\n//# sourceURL=webpack:///./src/App.vue?");

/***/ }),

/***/ "./src/app.config.json":
/*!*****************************!*\
  !*** ./src/app.config.json ***!
  \*****************************/
/*! exports provided: title, description, default */
/***/ (function(module) {

eval("module.exports = JSON.parse(\"{\\\"title\\\":\\\"Mask Of PCN\\\",\\\"description\\\":\\\"Search engine solution\\\"}\");\n\n//# sourceURL=webpack:///./src/app.config.json?");

/***/ }),

/***/ "./src/assets/images/error-img.png":
/*!*****************************************!*\
  !*** ./src/assets/images/error-img.png ***!
  \*****************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("module.exports = __webpack_require__.p + \"img/error-img.c7ae99ce.png\";\n\n//# sourceURL=webpack:///./src/assets/images/error-img.png?");

/***/ }),

/***/ "./src/main.js":
/*!*********************!*\
  !*** ./src/main.js ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_array_iterator_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./node_modules/core-js/modules/es.array.iterator.js */ \"./node_modules/core-js/modules/es.array.iterator.js\");\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_array_iterator_js__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_array_iterator_js__WEBPACK_IMPORTED_MODULE_0__);\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_promise_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./node_modules/core-js/modules/es.promise.js */ \"./node_modules/core-js/modules/es.promise.js\");\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_promise_js__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_promise_js__WEBPACK_IMPORTED_MODULE_1__);\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_object_assign_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./node_modules/core-js/modules/es.object.assign.js */ \"./node_modules/core-js/modules/es.object.assign.js\");\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_object_assign_js__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_object_assign_js__WEBPACK_IMPORTED_MODULE_2__);\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_promise_finally_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./node_modules/core-js/modules/es.promise.finally.js */ \"./node_modules/core-js/modules/es.promise.finally.js\");\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_promise_finally_js__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_core_js_modules_es_promise_finally_js__WEBPACK_IMPORTED_MODULE_3__);\n/* harmony import */ var vue__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! vue */ \"./node_modules/vue/dist/vue.runtime.esm.js\");\n/* harmony import */ var _router_index__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./router/index */ \"./src/router/index.js\");\n/* harmony import */ var _App_vue__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./App.vue */ \"./src/App.vue\");\n\n\n\n\n\n //import store from '@/state/store'\n\n\nnew vue__WEBPACK_IMPORTED_MODULE_4__[\"default\"]({\n  router: _router_index__WEBPACK_IMPORTED_MODULE_5__[\"default\"],\n  //store,\n  render: function render(h) {\n    return h(_App_vue__WEBPACK_IMPORTED_MODULE_6__[\"default\"]);\n  }\n}).$mount('#app');\n/*\nimport Vue from 'vue'\nimport App from './App.vue'\n\nVue.config.productionTip = false\n\nnew Vue({\n  render: h => h(App),\n}).$mount('#app')\n\n*/\n\n//# sourceURL=webpack:///./src/main.js?");

/***/ }),

/***/ "./src/router/index.js":
/*!*****************************!*\
  !*** ./src/router/index.js ***!
  \*****************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var core_js_modules_es_array_some__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! core-js/modules/es.array.some */ \"./node_modules/core-js/modules/es.array.some.js\");\n/* harmony import */ var core_js_modules_es_array_some__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_es_array_some__WEBPACK_IMPORTED_MODULE_0__);\n/* harmony import */ var core_js_modules_es_object_to_string__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! core-js/modules/es.object.to-string */ \"./node_modules/core-js/modules/es.object.to-string.js\");\n/* harmony import */ var core_js_modules_es_object_to_string__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_es_object_to_string__WEBPACK_IMPORTED_MODULE_1__);\n/* harmony import */ var core_js_modules_es_string_iterator__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! core-js/modules/es.string.iterator */ \"./node_modules/core-js/modules/es.string.iterator.js\");\n/* harmony import */ var core_js_modules_es_string_iterator__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_es_string_iterator__WEBPACK_IMPORTED_MODULE_2__);\n/* harmony import */ var core_js_modules_web_dom_collections_iterator__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! core-js/modules/web.dom-collections.iterator */ \"./node_modules/core-js/modules/web.dom-collections.iterator.js\");\n/* harmony import */ var core_js_modules_web_dom_collections_iterator__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_web_dom_collections_iterator__WEBPACK_IMPORTED_MODULE_3__);\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_createForOfIteratorHelper__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./node_modules/@babel/runtime/helpers/esm/createForOfIteratorHelper */ \"./node_modules/@babel/runtime/helpers/esm/createForOfIteratorHelper.js\");\n/* harmony import */ var regenerator_runtime_runtime__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! regenerator-runtime/runtime */ \"./node_modules/regenerator-runtime/runtime.js\");\n/* harmony import */ var regenerator_runtime_runtime__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(regenerator_runtime_runtime__WEBPACK_IMPORTED_MODULE_5__);\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_asyncToGenerator__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./node_modules/@babel/runtime/helpers/esm/asyncToGenerator */ \"./node_modules/@babel/runtime/helpers/esm/asyncToGenerator.js\");\n/* harmony import */ var vue__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! vue */ \"./node_modules/vue/dist/vue.runtime.esm.js\");\n/* harmony import */ var vue_router__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! vue-router */ \"./node_modules/vue-router/dist/vue-router.esm.js\");\n!(function webpackMissingModule() { var e = new Error(\"Cannot find module 'vue-meta'\"); e.code = 'MODULE_NOT_FOUND'; throw e; }());\n/* harmony import */ var _state_store__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @/state/store */ \"./src/state/store.js\");\n/* harmony import */ var _routes__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./routes */ \"./src/router/routes.js\");\n/* harmony import */ var axios__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! axios */ \"./node_modules/axios/index.js\");\n/* harmony import */ var axios__WEBPACK_IMPORTED_MODULE_12___default = /*#__PURE__*/__webpack_require__.n(axios__WEBPACK_IMPORTED_MODULE_12__);\n\n\n\n\n\n\n\n\n\n\n\n\n\nvue__WEBPACK_IMPORTED_MODULE_7__[\"default\"].use(vue_router__WEBPACK_IMPORTED_MODULE_8__[\"default\"]);\nvue__WEBPACK_IMPORTED_MODULE_7__[\"default\"].use(!(function webpackMissingModule() { var e = new Error(\"Cannot find module 'vue-meta'\"); e.code = 'MODULE_NOT_FOUND'; throw e; }()), {\n  // The component option name that vue-meta looks for meta info on.\n  keyName: 'page'\n});\nvar router = new vue_router__WEBPACK_IMPORTED_MODULE_8__[\"default\"]({\n  routes: _routes__WEBPACK_IMPORTED_MODULE_11__[\"default\"],\n  // Use the HTML5 history API (i.e. normal-looking routes)\n  // instead of routes with hashes (e.g. example.com/#/about).\n  // This may require some server configuration in production:\n  // https://router.vuejs.org/en/essentials/history-mode.html#example-server-configurations\n  mode: 'history',\n  // Simulate native-like scroll behavior when navigating to a new\n  // route and using back/forward buttons.\n  scrollBehavior: function scrollBehavior(to, from, savedPosition) {\n    if (savedPosition) {\n      return savedPosition;\n    } else {\n      return {\n        x: 0,\n        y: 0\n      };\n    }\n  }\n}); // Before each route evaluates...\n\nrouter.beforeEach(function (routeTo, routeFrom, next) {\n  // Check if auth is required on this route\n  // (including nested routes).\n  var authRequired = routeTo.matched.some(function (route) {\n    return route.meta.authRequired;\n  }); // If auth isn't required for the route, just continue.\n\n  if (!authRequired || _state_store__WEBPACK_IMPORTED_MODULE_10__[\"default\"].state.isLogin) {\n    return next();\n  }\n\n  next({\n    name: 'login',\n    query: {\n      redirectFrom: routeTo.fullPath\n    }\n  });\n});\nrouter.beforeResolve( /*#__PURE__*/function () {\n  var _ref = Object(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_asyncToGenerator__WEBPACK_IMPORTED_MODULE_6__[\"default\"])( /*#__PURE__*/regeneratorRuntime.mark(function _callee(routeTo, routeFrom, next) {\n    var _iterator, _step, _loop;\n\n    return regeneratorRuntime.wrap(function _callee$(_context2) {\n      while (1) {\n        switch (_context2.prev = _context2.next) {\n          case 0:\n            _context2.prev = 0;\n            // For each matched route...\n            _iterator = Object(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_createForOfIteratorHelper__WEBPACK_IMPORTED_MODULE_4__[\"default\"])(routeTo.matched);\n            _context2.prev = 2;\n            _loop = /*#__PURE__*/regeneratorRuntime.mark(function _loop() {\n              var route;\n              return regeneratorRuntime.wrap(function _loop$(_context) {\n                while (1) {\n                  switch (_context.prev = _context.next) {\n                    case 0:\n                      route = _step.value;\n                      _context.next = 3;\n                      return new Promise(function (resolve, reject) {\n                        // If a `beforeResolve` hook is defined, call it with\n                        // the same arguments as the `beforeEnter` hook.\n                        if (route.meta && route.meta.beforeResolve) {\n                          route.meta.beforeResolve(routeTo, routeFrom, function () {\n                            // If the user chose to redirect...\n                            if (arguments.length) {\n                              // If redirecting to the same route we're coming from...\n                              // Complete the redirect.\n                              next.apply(void 0, arguments);\n                              reject(new Error('Redirected'));\n                            } else {\n                              resolve();\n                            }\n                          });\n                        } else {\n                          // Otherwise, continue resolving the route.\n                          resolve();\n                        }\n                      });\n\n                    case 3:\n                    case \"end\":\n                      return _context.stop();\n                  }\n                }\n              }, _loop);\n            });\n\n            _iterator.s();\n\n          case 5:\n            if ((_step = _iterator.n()).done) {\n              _context2.next = 9;\n              break;\n            }\n\n            return _context2.delegateYield(_loop(), \"t0\", 7);\n\n          case 7:\n            _context2.next = 5;\n            break;\n\n          case 9:\n            _context2.next = 14;\n            break;\n\n          case 11:\n            _context2.prev = 11;\n            _context2.t1 = _context2[\"catch\"](2);\n\n            _iterator.e(_context2.t1);\n\n          case 14:\n            _context2.prev = 14;\n\n            _iterator.f();\n\n            return _context2.finish(14);\n\n          case 17:\n            _context2.next = 22;\n            break;\n\n          case 19:\n            _context2.prev = 19;\n            _context2.t2 = _context2[\"catch\"](0);\n            return _context2.abrupt(\"return\");\n\n          case 22:\n            // If we reach this point, continue resolving the route.\n            next();\n\n          case 23:\n          case \"end\":\n            return _context2.stop();\n        }\n      }\n    }, _callee, null, [[0, 19], [2, 11, 14, 17]]);\n  }));\n\n  return function (_x, _x2, _x3) {\n    return _ref.apply(this, arguments);\n  };\n}());\nvue__WEBPACK_IMPORTED_MODULE_7__[\"default\"].prototype.$http = axios__WEBPACK_IMPORTED_MODULE_12___default.a;\naxios__WEBPACK_IMPORTED_MODULE_12___default.a.interceptors.request.use(function (config) {\n  config.headers[\"Authorization\"] = \"Bearer \".concat(_state_store__WEBPACK_IMPORTED_MODULE_10__[\"default\"].state.token);\n  config.baseURL = \"http://localhost:8081\" + \"/api/v1\";\n  return config;\n}, function (error) {\n  return Promise.reject(error);\n});\naxios__WEBPACK_IMPORTED_MODULE_12___default.a.interceptors.response.use(function (response) {\n  return response;\n}, function (error) {\n  alert(error.response.data.body);\n  return Promise.reject(error);\n});\n/* harmony default export */ __webpack_exports__[\"default\"] = (router);\n\n//# sourceURL=webpack:///./src/router/index.js?");

/***/ }),

/***/ "./src/router/routes.js":
/*!******************************!*\
  !*** ./src/router/routes.js ***!
  \******************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var core_js_modules_es_array_some__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! core-js/modules/es.array.some */ \"./node_modules/core-js/modules/es.array.some.js\");\n/* harmony import */ var core_js_modules_es_array_some__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_es_array_some__WEBPACK_IMPORTED_MODULE_0__);\n/* harmony import */ var core_js_modules_es_object_to_string__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! core-js/modules/es.object.to-string */ \"./node_modules/core-js/modules/es.object.to-string.js\");\n/* harmony import */ var core_js_modules_es_object_to_string__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(core_js_modules_es_object_to_string__WEBPACK_IMPORTED_MODULE_1__);\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_objectSpread2__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./node_modules/@babel/runtime/helpers/esm/objectSpread2 */ \"./node_modules/@babel/runtime/helpers/esm/objectSpread2.js\");\n/* harmony import */ var _state_store__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @/state/store */ \"./src/state/store.js\");\n\n\n\n\n/* harmony default export */ __webpack_exports__[\"default\"] = ([{\n  path: '/',\n  name: 'home',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(76)]).then(__webpack_require__.bind(null, /*! ./views/home */ \"./src/router/views/home.vue\"));\n  }\n}, {\n  path: '/login',\n  name: 'login',\n  component: function component() {\n    return __webpack_require__.e(/*! import() */ 27).then(__webpack_require__.bind(null, /*! ./views/account/login */ \"./src/router/views/account/login.vue\"));\n  },\n  meta: {\n    beforeResolve: function beforeResolve(routeTo, routeFrom, next) {\n      // If the user is already logged in\n      if (_state_store__WEBPACK_IMPORTED_MODULE_3__[\"default\"].getters['auth/loggedIn']) {\n        // Redirect to the home page instead\n        next({\n          name: 'home'\n        });\n      } else {\n        // Continue to the login page\n        next();\n      }\n    }\n  }\n}, {\n  path: '/logout',\n  name: 'logout',\n  meta: {\n    authRequired: false,\n    beforeResolve: function beforeResolve(routeTo, routeFrom, next) {\n      _state_store__WEBPACK_IMPORTED_MODULE_3__[\"default\"].dispatch('auth/logOut');\n      var authRequiredOnPreviousRoute = routeFrom.matched.some(function (route) {\n        return route.push('/login');\n      }); // Navigate back to previous page, or home as a fallback\n\n      next(authRequiredOnPreviousRoute ? {\n        name: 'home'\n      } : Object(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_objectSpread2__WEBPACK_IMPORTED_MODULE_2__[\"default\"])({}, routeFrom));\n    }\n  }\n}, {\n  path: '/404',\n  name: '404',\n  component: __webpack_require__(/*! ./views/utility/404 */ \"./src/router/views/utility/404.vue\").default\n}, // Redirect any unmatched routes to the 404 page. This may\n// require some server configuration to work in production:\n// https://router.vuejs.org/en/essentials/history-mode.html#example-server-configurations\n{\n  path: '*',\n  redirect: '404'\n}, {\n  path: '/calendar',\n  name: 'Calendar',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(6)]).then(__webpack_require__.bind(null, /*! ./views/calendar/calendar */ \"./src/router/views/calendar/calendar.vue\"));\n  }\n}, {\n  path: '/ecommerce/products',\n  name: 'Products',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(14)]).then(__webpack_require__.bind(null, /*! ./views/ecommerce/products */ \"./src/router/views/ecommerce/products.vue\"));\n  }\n}, {\n  path: '/ecommerce/product-detail',\n  name: 'Product Detail',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(23)]).then(__webpack_require__.bind(null, /*! ./views/ecommerce/product-detail */ \"./src/router/views/ecommerce/product-detail.vue\"));\n  }\n}, {\n  path: '/ecommerce/orders',\n  name: 'Orders',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(3), __webpack_require__.e(38)]).then(__webpack_require__.bind(null, /*! ./views/ecommerce/orders */ \"./src/router/views/ecommerce/orders.vue\"));\n  }\n}, {\n  path: '/ecommerce/customers',\n  name: 'Customers',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(28)]).then(__webpack_require__.bind(null, /*! ./views/ecommerce/customers */ \"./src/router/views/ecommerce/customers.vue\"));\n  }\n}, {\n  path: '/ecommerce/cart',\n  name: 'Cart',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(17)]).then(__webpack_require__.bind(null, /*! ./views/ecommerce/cart */ \"./src/router/views/ecommerce/cart.vue\"));\n  }\n}, {\n  path: '/ecommerce/checkout',\n  name: 'Checkout',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(26)]).then(__webpack_require__.bind(null, /*! ./views/ecommerce/checkout */ \"./src/router/views/ecommerce/checkout.vue\"));\n  }\n}, {\n  path: '/ecommerce/shops',\n  name: 'Shops',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(29)]).then(__webpack_require__.bind(null, /*! ./views/ecommerce/shops */ \"./src/router/views/ecommerce/shops.vue\"));\n  }\n}, {\n  path: '/ecommerce/add-product',\n  name: 'Add Product',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(37)]).then(__webpack_require__.bind(null, /*! ./views/ecommerce/add-product */ \"./src/router/views/ecommerce/add-product.vue\"));\n  }\n}, {\n  path: '/invoices/detail',\n  name: 'Invoice Detail',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(49)]).then(__webpack_require__.bind(null, /*! ./views/invoices/detail */ \"./src/router/views/invoices/detail.vue\"));\n  }\n}, {\n  path: '/invoices/list',\n  name: 'Invoice List',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(18)]).then(__webpack_require__.bind(null, /*! ./views/invoices/list */ \"./src/router/views/invoices/list.vue\"));\n  }\n}, {\n  path: '/ui/alerts',\n  name: 'Alerts',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(52)]).then(__webpack_require__.bind(null, /*! ./views/ui/alerts */ \"./src/router/views/ui/alerts.vue\"));\n  }\n}, {\n  path: '/ui/buttons',\n  name: 'Buttons',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(53)]).then(__webpack_require__.bind(null, /*! ./views/ui/buttons */ \"./src/router/views/ui/buttons.vue\"));\n  }\n}, {\n  path: '/ui/cards',\n  name: 'Cards',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(15)]).then(__webpack_require__.bind(null, /*! ./views/ui/cards */ \"./src/router/views/ui/cards.vue\"));\n  }\n}, {\n  path: '/ui/carousel',\n  name: 'Carousel',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(16)]).then(__webpack_require__.bind(null, /*! ./views/ui/carousel */ \"./src/router/views/ui/carousel.vue\"));\n  }\n}, {\n  path: '/ui/dropdowns',\n  name: 'Dropdowns',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(55)]).then(__webpack_require__.bind(null, /*! ./views/ui/dropdowns */ \"./src/router/views/ui/dropdowns.vue\"));\n  }\n}, {\n  path: '/ui/grid',\n  name: 'Grid',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(57)]).then(__webpack_require__.bind(null, /*! ./views/ui/grid */ \"./src/router/views/ui/grid.vue\"));\n  }\n}, {\n  path: '/ui/images',\n  name: 'Images',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(10)]).then(__webpack_require__.bind(null, /*! ./views/ui/images */ \"./src/router/views/ui/images.vue\"));\n  }\n}, {\n  path: '/ui/modals',\n  name: 'Modals',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(58)]).then(__webpack_require__.bind(null, /*! ./views/ui/modals */ \"./src/router/views/ui/modals.vue\"));\n  }\n}, {\n  path: '/ui/rangeslider',\n  name: 'Rangeslider',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(60)]).then(__webpack_require__.bind(null, /*! ./views/ui/rangeslider */ \"./src/router/views/ui/rangeslider.vue\"));\n  }\n}, {\n  path: '/ui/progressbars',\n  name: 'Progressbars',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(59)]).then(__webpack_require__.bind(null, /*! ./views/ui/progressbars */ \"./src/router/views/ui/progressbars.vue\"));\n  }\n}, {\n  path: '/ui/sweet-alert',\n  name: 'Sweet-alert',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(61)]).then(__webpack_require__.bind(null, /*! ./views/ui/sweet-alert */ \"./src/router/views/ui/sweet-alert.vue\"));\n  }\n}, {\n  path: '/ui/tabs-accordions',\n  name: 'Tabs-accordions',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(62)]).then(__webpack_require__.bind(null, /*! ./views/ui/tabs-accordions */ \"./src/router/views/ui/tabs-accordions.vue\"));\n  }\n}, {\n  path: '/ui/typography',\n  name: 'Typography',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(63)]).then(__webpack_require__.bind(null, /*! ./views/ui/typography */ \"./src/router/views/ui/typography.vue\"));\n  }\n}, {\n  path: '/ui/video',\n  name: 'Video',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(64)]).then(__webpack_require__.bind(null, /*! ./views/ui/video */ \"./src/router/views/ui/video.vue\"));\n  }\n}, {\n  path: '/ui/general',\n  name: 'General',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(56)]).then(__webpack_require__.bind(null, /*! ./views/ui/general */ \"./src/router/views/ui/general.vue\"));\n  }\n}, {\n  path: '/ui/colors',\n  name: 'Colors',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(54)]).then(__webpack_require__.bind(null, /*! ./views/ui/colors */ \"./src/router/views/ui/colors.vue\"));\n  }\n}, {\n  path: '/projects/grid',\n  name: 'Projects Grid',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(8)]).then(__webpack_require__.bind(null, /*! ./views/projects/projects-grid */ \"./src/router/views/projects/projects-grid.vue\"));\n  }\n}, {\n  path: '/projects/list',\n  name: 'Projects List',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(9)]).then(__webpack_require__.bind(null, /*! ./views/projects/projects-list */ \"./src/router/views/projects/projects-list.vue\"));\n  }\n}, {\n  path: '/projects/overview',\n  name: 'Project Overview',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(19)]).then(__webpack_require__.bind(null, /*! ./views/projects/overview */ \"./src/router/views/projects/overview.vue\"));\n  }\n}, {\n  path: '/projects/create',\n  name: 'Create New',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(50)]).then(__webpack_require__.bind(null, /*! ./views/projects/create */ \"./src/router/views/projects/create.vue\"));\n  }\n}, {\n  path: '/contacts/grid',\n  name: 'User Grid',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(20)]).then(__webpack_require__.bind(null, /*! ./views/contacts/contacts-grid */ \"./src/router/views/contacts/contacts-grid.vue\"));\n  }\n}, {\n  path: '/contacts/list',\n  name: 'User List',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(21)]).then(__webpack_require__.bind(null, /*! ./views/contacts/contacts-list */ \"./src/router/views/contacts/contacts-list.vue\"));\n  }\n}, {\n  path: '/contacts/profile',\n  name: 'Profile',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(11)]).then(__webpack_require__.bind(null, /*! ./views/contacts/contacts-profile */ \"./src/router/views/contacts/contacts-profile.vue\"));\n  }\n}, {\n  path: '/charts/apex',\n  name: 'Apex chart',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(22)]).then(__webpack_require__.bind(null, /*! ./views/charts/apex */ \"./src/router/views/charts/apex.vue\"));\n  }\n}, {\n  path: '/charts/chartjs',\n  name: 'Chartjs chart',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(7)]).then(__webpack_require__.bind(null, /*! ./views/charts/chartjs/index */ \"./src/router/views/charts/chartjs/index.vue\"));\n  }\n}, {\n  path: '/charts/chartist',\n  name: 'Chartist chart',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(2), __webpack_require__.e(35)]).then(__webpack_require__.bind(null, /*! ./views/charts/chartist */ \"./src/router/views/charts/chartist.vue\"));\n  }\n}, {\n  path: '/icons/boxicons',\n  name: 'Boxicons Icon',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(45)]).then(__webpack_require__.bind(null, /*! ./views/icons/boxicons */ \"./src/router/views/icons/boxicons.vue\"));\n  }\n}, {\n  path: '/icons/materialdesign',\n  name: 'Materialdesign Icon',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(48)]).then(__webpack_require__.bind(null, /*! ./views/icons/materialdesign */ \"./src/router/views/icons/materialdesign.vue\"));\n  }\n}, {\n  path: '/icons/dripicons',\n  name: 'Dripicons Icon',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(46)]).then(__webpack_require__.bind(null, /*! ./views/icons/dripicons */ \"./src/router/views/icons/dripicons.vue\"));\n  }\n}, {\n  path: '/icons/fontawesome',\n  name: 'Fontawesome Icon',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(47)]).then(__webpack_require__.bind(null, /*! ./views/icons/fontawesome */ \"./src/router/views/icons/fontawesome.vue\"));\n  }\n}, {\n  path: '/document/documents',\n  name: 'Documents',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(2), __webpack_require__.e(69)]).then(__webpack_require__.bind(null, /*! ./views/document/documents */ \"./src/router/views/document/documents.vue\"));\n  }\n}, {\n  path: '/index/indices',\n  name: 'Indices',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(31)]).then(__webpack_require__.bind(null, /*! ./views/index/indices */ \"./src/router/views/index/indices.vue\"));\n  }\n}, {\n  path: '/pipeline/pipelines',\n  name: 'Pipelines',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(4)]).then(__webpack_require__.bind(null, /*! ./views/pipeline/pipelines */ \"./src/router/views/pipeline/pipelines.vue\"));\n  }\n}, {\n  path: '/pipeline/create',\n  name: 'Create Pipelines',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(2), __webpack_require__.e(36)]).then(__webpack_require__.bind(null, /*! ./views/pipeline/create */ \"./src/router/views/pipeline/create.vue\"));\n  }\n}, {\n  path: '/schedule/schedules',\n  name: 'Schedules',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(32)]).then(__webpack_require__.bind(null, /*! ./views/schedule/schedules */ \"./src/router/views/schedule/schedules.vue\"));\n  }\n}, {\n  path: '/form/advanced',\n  name: 'Form Advanced',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(39)]).then(__webpack_require__.bind(null, /*! ./views/forms/advanced */ \"./src/router/views/forms/advanced.vue\"));\n  }\n}, {\n  path: '/form/elements',\n  name: 'Form Elements',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(40)]).then(__webpack_require__.bind(null, /*! ./views/forms/elements */ \"./src/router/views/forms/elements.vue\"));\n  }\n}, {\n  path: '/form/editor',\n  name: 'CK Editor',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(1), __webpack_require__.e(75)]).then(__webpack_require__.bind(null, /*! ./views/forms/ckeditor */ \"./src/router/views/forms/ckeditor.vue\"));\n  }\n}, {\n  path: '/form/uploads',\n  name: 'File Uploads',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(42)]).then(__webpack_require__.bind(null, /*! ./views/forms/uploads */ \"./src/router/views/forms/uploads.vue\"));\n  }\n}, {\n  path: '/form/validation',\n  name: 'Form Validation',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(43)]).then(__webpack_require__.bind(null, /*! ./views/forms/validation */ \"./src/router/views/forms/validation.vue\"));\n  }\n}, {\n  path: '/form/wizard',\n  name: 'Form Wizard',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(44)]).then(__webpack_require__.bind(null, /*! ./views/forms/wizard */ \"./src/router/views/forms/wizard.vue\"));\n  }\n}, {\n  path: '/form/repeater',\n  name: 'Form Repeater',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(30)]).then(__webpack_require__.bind(null, /*! ./views/forms/repeater */ \"./src/router/views/forms/repeater.vue\"));\n  }\n}, {\n  path: '/form/mask',\n  name: 'Form Mask',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(41)]).then(__webpack_require__.bind(null, /*! ./views/forms/mask */ \"./src/router/views/forms/mask.vue\"));\n  }\n}, {\n  path: '/tables/basictable',\n  name: 'Basic table',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(51)]).then(__webpack_require__.bind(null, /*! ./views/tables/basictable */ \"./src/router/views/tables/basictable.vue\"));\n  }\n}, {\n  path: '/tables/advancedtable',\n  name: 'Advanced Table',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(33)]).then(__webpack_require__.bind(null, /*! ./views/tables/advancedtable */ \"./src/router/views/tables/advancedtable.vue\"));\n  }\n}, {\n  path: '/pages/starter',\n  name: 'Starter',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(66)]).then(__webpack_require__.bind(null, /*! ./views/utility/starter */ \"./src/router/views/utility/starter.vue\"));\n  }\n}, {\n  path: '/pages/maintenance',\n  name: 'Maintenance',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return __webpack_require__.e(/*! import() */ 73).then(__webpack_require__.bind(null, /*! ./views/utility/maintenance */ \"./src/router/views/utility/maintenance.vue\"));\n  }\n}, {\n  path: '/pages/timeline',\n  name: 'Timeline',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(67)]).then(__webpack_require__.bind(null, /*! ./views/utility/timeline */ \"./src/router/views/utility/timeline.vue\"));\n  }\n}, {\n  path: '/pages/faqs',\n  name: 'FAQs',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(65)]).then(__webpack_require__.bind(null, /*! ./views/utility/faqs */ \"./src/router/views/utility/faqs.vue\"));\n  }\n}, {\n  path: '/pages/pricing',\n  name: 'Pricing',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(34)]).then(__webpack_require__.bind(null, /*! ./views/utility/pricing */ \"./src/router/views/utility/pricing.vue\"));\n  }\n}, {\n  path: '/pages/404',\n  name: 'Error-404',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.resolve(/*! import() */).then(__webpack_require__.bind(null, /*! ./views/utility/404 */ \"./src/router/views/utility/404.vue\"));\n  }\n}, {\n  path: '/pages/500',\n  name: 'Error-500',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return __webpack_require__.e(/*! import() */ 77).then(__webpack_require__.bind(null, /*! ./views/utility/500 */ \"./src/router/views/utility/500.vue\"));\n  }\n}, {\n  path: '/email/inbox',\n  name: 'Inbox',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(1), __webpack_require__.e(25)]).then(__webpack_require__.bind(null, /*! ./views/email/inbox */ \"./src/router/views/email/inbox.vue\"));\n  }\n}, {\n  path: '/email/reademail',\n  name: 'Read Email',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(1), __webpack_require__.e(24)]).then(__webpack_require__.bind(null, /*! ./views/email/reademail */ \"./src/router/views/email/reademail.vue\"));\n  }\n}, {\n  path: '/tasks/list',\n  name: 'Task list',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(13)]).then(__webpack_require__.bind(null, /*! ./views/tasks/task-list */ \"./src/router/views/tasks/task-list.vue\"));\n  }\n}, {\n  path: '/tasks/kanban',\n  name: 'Kanbanboard',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(12)]).then(__webpack_require__.bind(null, /*! ./views/tasks/kanbanboard */ \"./src/router/views/tasks/kanbanboard.vue\"));\n  }\n}, {\n  path: '/tasks/create',\n  name: 'Create Task',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(0), __webpack_require__.e(1), __webpack_require__.e(74)]).then(__webpack_require__.bind(null, /*! ./views/tasks/task-create */ \"./src/router/views/tasks/task-create.vue\"));\n  }\n}, {\n  path: '/layout/horizontal',\n  name: 'Horizontal',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return Promise.all(/*! import() */[__webpack_require__.e(3), __webpack_require__.e(5)]).then(__webpack_require__.bind(null, /*! ./views/layout/horizontal */ \"./src/router/views/layout/horizontal.vue\"));\n  }\n}, {\n  path: '/auth/login-1',\n  name: 'Login sample',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return __webpack_require__.e(/*! import() */ 70).then(__webpack_require__.bind(null, /*! ./views/sample-pages/login-sample */ \"./src/router/views/sample-pages/login-sample.vue\"));\n  }\n}, {\n  path: '/auth/register-1',\n  name: 'Register sample',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return __webpack_require__.e(/*! import() */ 72).then(__webpack_require__.bind(null, /*! ./views/sample-pages/register-sample */ \"./src/router/views/sample-pages/register-sample.vue\"));\n  }\n}, {\n  path: '/auth/recoverpw',\n  name: 'Recover pwd',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return __webpack_require__.e(/*! import() */ 71).then(__webpack_require__.bind(null, /*! ./views/sample-pages/recoverpw-sample */ \"./src/router/views/sample-pages/recoverpw-sample.vue\"));\n  }\n}, {\n  path: '/auth/lock-screen',\n  name: 'Lock screen',\n  meta: {\n    authRequired: true\n  },\n  component: function component() {\n    return __webpack_require__.e(/*! import() */ 68).then(__webpack_require__.bind(null, /*! ./views/sample-pages/lockscreen */ \"./src/router/views/sample-pages/lockscreen.vue\"));\n  }\n}]);\n\n//# sourceURL=webpack:///./src/router/routes.js?");

/***/ }),

/***/ "./src/router/views/utility/404.vue":
/*!******************************************!*\
  !*** ./src/router/views/utility/404.vue ***!
  \******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _404_vue_vue_type_template_id_7c5eae87___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./404.vue?vue&type=template&id=7c5eae87& */ \"./src/router/views/utility/404.vue?vue&type=template&id=7c5eae87&\");\n/* harmony import */ var _404_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./404.vue?vue&type=script&lang=js& */ \"./src/router/views/utility/404.vue?vue&type=script&lang=js&\");\n/* empty/unused harmony star reexport *//* harmony import */ var _node_modules_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../node_modules/vue-loader/lib/runtime/componentNormalizer.js */ \"./node_modules/vue-loader/lib/runtime/componentNormalizer.js\");\n\n\n\n\n\n/* normalize component */\n\nvar component = Object(_node_modules_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_2__[\"default\"])(\n  _404_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[\"default\"],\n  _404_vue_vue_type_template_id_7c5eae87___WEBPACK_IMPORTED_MODULE_0__[\"render\"],\n  _404_vue_vue_type_template_id_7c5eae87___WEBPACK_IMPORTED_MODULE_0__[\"staticRenderFns\"],\n  false,\n  null,\n  null,\n  null\n  \n)\n\n/* hot reload */\nif (false) { var api; }\ncomponent.options.__file = \"src/router/views/utility/404.vue\"\n/* harmony default export */ __webpack_exports__[\"default\"] = (component.exports);\n\n//# sourceURL=webpack:///./src/router/views/utility/404.vue?");

/***/ }),

/***/ "./src/router/views/utility/404.vue?vue&type=script&lang=js&":
/*!*******************************************************************!*\
  !*** ./src/router/views/utility/404.vue?vue&type=script&lang=js& ***!
  \*******************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _node_modules_cache_loader_dist_cjs_js_ref_12_0_node_modules_babel_loader_lib_index_js_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_404_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../node_modules/cache-loader/dist/cjs.js??ref--12-0!../../../../node_modules/babel-loader/lib!../../../../node_modules/cache-loader/dist/cjs.js??ref--0-0!../../../../node_modules/vue-loader/lib??vue-loader-options!./404.vue?vue&type=script&lang=js& */ \"./node_modules/cache-loader/dist/cjs.js?!./node_modules/babel-loader/lib/index.js!./node_modules/cache-loader/dist/cjs.js?!./node_modules/vue-loader/lib/index.js?!./src/router/views/utility/404.vue?vue&type=script&lang=js&\");\n/* empty/unused harmony star reexport */ /* harmony default export */ __webpack_exports__[\"default\"] = (_node_modules_cache_loader_dist_cjs_js_ref_12_0_node_modules_babel_loader_lib_index_js_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_404_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[\"default\"]); \n\n//# sourceURL=webpack:///./src/router/views/utility/404.vue?");

/***/ }),

/***/ "./src/router/views/utility/404.vue?vue&type=template&id=7c5eae87&":
/*!*************************************************************************!*\
  !*** ./src/router/views/utility/404.vue?vue&type=template&id=7c5eae87& ***!
  \*************************************************************************/
/*! exports provided: render, staticRenderFns */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _node_modules_cache_loader_dist_cjs_js_cacheDirectory_node_modules_cache_vue_loader_cacheIdentifier_da1db418_vue_loader_template_node_modules_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_404_vue_vue_type_template_id_7c5eae87___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!../../../../node_modules/cache-loader/dist/cjs.js?{\"cacheDirectory\":\"node_modules/.cache/vue-loader\",\"cacheIdentifier\":\"da1db418-vue-loader-template\"}!../../../../node_modules/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!../../../../node_modules/cache-loader/dist/cjs.js??ref--0-0!../../../../node_modules/vue-loader/lib??vue-loader-options!./404.vue?vue&type=template&id=7c5eae87& */ \"./node_modules/cache-loader/dist/cjs.js?{\\\"cacheDirectory\\\":\\\"node_modules/.cache/vue-loader\\\",\\\"cacheIdentifier\\\":\\\"da1db418-vue-loader-template\\\"}!./node_modules/vue-loader/lib/loaders/templateLoader.js?!./node_modules/cache-loader/dist/cjs.js?!./node_modules/vue-loader/lib/index.js?!./src/router/views/utility/404.vue?vue&type=template&id=7c5eae87&\");\n/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, \"render\", function() { return _node_modules_cache_loader_dist_cjs_js_cacheDirectory_node_modules_cache_vue_loader_cacheIdentifier_da1db418_vue_loader_template_node_modules_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_404_vue_vue_type_template_id_7c5eae87___WEBPACK_IMPORTED_MODULE_0__[\"render\"]; });\n\n/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, \"staticRenderFns\", function() { return _node_modules_cache_loader_dist_cjs_js_cacheDirectory_node_modules_cache_vue_loader_cacheIdentifier_da1db418_vue_loader_template_node_modules_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_node_modules_cache_loader_dist_cjs_js_ref_0_0_node_modules_vue_loader_lib_index_js_vue_loader_options_404_vue_vue_type_template_id_7c5eae87___WEBPACK_IMPORTED_MODULE_0__[\"staticRenderFns\"]; });\n\n\n\n//# sourceURL=webpack:///./src/router/views/utility/404.vue?");

/***/ }),

/***/ "./src/state/store.js":
/*!****************************!*\
  !*** ./src/state/store.js ***!
  \****************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_defineProperty__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./node_modules/@babel/runtime/helpers/esm/defineProperty */ \"./node_modules/@babel/runtime/helpers/esm/defineProperty.js\");\n/* harmony import */ var vue__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! vue */ \"./node_modules/vue/dist/vue.runtime.esm.js\");\n!(function webpackMissingModule() { var e = new Error(\"Cannot find module 'vuex'\"); e.code = 'MODULE_NOT_FOUND'; throw e; }());\n!(function webpackMissingModule() { var e = new Error(\"Cannot find module 'vuex-persist'\"); e.code = 'MODULE_NOT_FOUND'; throw e; }());\n\n\nvar _mutations;\n\n\n\n\nvar vuexPersistence = new !(function webpackMissingModule() { var e = new Error(\"Cannot find module 'vuex-persist'\"); e.code = 'MODULE_NOT_FOUND'; throw e; }())({\n  key: 'vuex-state',\n  storage: window.sessionStorage\n});\nvue__WEBPACK_IMPORTED_MODULE_1__[\"default\"].use(!(function webpackMissingModule() { var e = new Error(\"Cannot find module 'vuex'\"); e.code = 'MODULE_NOT_FOUND'; throw e; }()));\nvar store = new !(function webpackMissingModule() { var e = new Error(\"Cannot find module 'vuex'\"); e.code = 'MODULE_NOT_FOUND'; throw e; }()).Store({\n  state: {\n    isLogin: false,\n    token: ''\n  },\n  mutations: (_mutations = {}, Object(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_defineProperty__WEBPACK_IMPORTED_MODULE_0__[\"default\"])(_mutations, 'SETTOKEN', function SETTOKEN(state, payload) {\n    state.token = payload;\n  }), Object(C_Users_pcn_IntellijProjects_bigdata_cooperation_module_src_bd_cooperation_node_modules_babel_runtime_helpers_esm_defineProperty__WEBPACK_IMPORTED_MODULE_0__[\"default\"])(_mutations, 'SETLOGINSTATE', function SETLOGINSTATE(state, payload) {\n    state.isLogin = payload;\n  }), _mutations),\n  plugins: [vuexPersistence.plugin],\n  // Enable strict mode in development to get a warning\n  // when mutating state outside of a mutation.\n  // https://vuex.vuejs.org/guide/strict.html\n  strict: \"development\" !== 'production'\n});\n/* harmony default export */ __webpack_exports__[\"default\"] = (store);\n\n//# sourceURL=webpack:///./src/state/store.js?");

/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.js ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("module.exports = __webpack_require__(/*! ./src/main.js */\"./src/main.js\");\n\n\n//# sourceURL=webpack:///multi_./src/main.js?");

/***/ })

/******/ });