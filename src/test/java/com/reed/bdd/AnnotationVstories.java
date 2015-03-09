package com.reed.bdd;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.web.selenium.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.reed.bdd.Vstories;

import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.web.selenium.WebDriverHtmlOutput.WEB_DRIVER_HTML;

/**
 * 在IDE里debug时只需要将预调试的Story或Scenario的Meta中增加@debug即可
 */
@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure(using = SeleniumConfiguration.class, pendingStepStrategy = FailingUponPendingStep.class)
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = false, storyTimeoutInSecs = 100, threads = 1, metaFilters = "+debug")
@UsingSpring(resources = { "com/reed/bdd/test/spring-context.xml" })
public class AnnotationVstories extends InjectableEmbedder {
	@Test
	public void run() throws Throwable {
		System.setProperty("webdriver.firefox.bin",
				"D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		CrossReference crossReference = new CrossReference().withJsonOnly()
				.withOutputAfterEachStory(true)
				.excludingStoriesWithNoExecutedScenarios(true);
		ContextView contextView = new LocalFrameContextView().sized(640, 120);
		SeleniumContext seleniumContext = new SeleniumContext();
		SeleniumStepMonitor stepMonitor = new SeleniumStepMonitor(contextView,
				seleniumContext, crossReference.getStepMonitor());
		Format[] formats = new Format[] {
				new SeleniumContextOutput(seleniumContext), CONSOLE,
				WEB_DRIVER_HTML, Format.STATS };
		StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
				.withCodeLocation(codeLocationFromClass(Vstories.class))
				.withFailureTrace(true).withFailureTraceCompression(true)
				.withDefaultFormats().withFormats(formats)
				.withCrossReference(crossReference);

		Configuration configuration = injectedEmbedder().configuration();
		configuration
				.useFailureStrategy(new FailingUponPendingStep())
				.useStoryControls(
						new StoryControls().doResetStateBeforeScenario(true))
				.useStepMonitor(stepMonitor)
				.useStoryLoader(new LoadFromClasspath(Vstories.class))
				.useStoryReporterBuilder(reporterBuilder);
		if (configuration instanceof SeleniumConfiguration) {
			SeleniumConfiguration seleniumConfiguration = (SeleniumConfiguration) configuration;
			seleniumConfiguration.useSeleniumContext(seleniumContext);
		}
		injectedEmbedder().runStoriesAsPaths(storyPaths());
	}

	protected List<String> storyPaths() {
		return new StoryFinder()
				.findPaths(
						codeLocationFromClass(this.getClass()).getFile(),
						asList("**/"
								+ System.getProperty("storyFilter",
										"test-baidu-search") + ".story"), null);
	}
}
