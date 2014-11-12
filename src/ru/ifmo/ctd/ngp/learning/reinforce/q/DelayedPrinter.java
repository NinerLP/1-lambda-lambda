package ru.ifmo.ctd.ngp.learning.reinforce.q;

import ru.ifmo.ctd.ngp.learning.reinforce.AgentPrinter;
import ru.ifmo.ctd.ngp.learning.reinforce.Environment;
import ru.ifmo.ctd.ngp.learning.util.Maps;
import ru.ifmo.ctd.ngp.learning.util.PrintFormatter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * {@link AgentPrinter} that prints information 
 * about {@link DelayedAgent}
 *  
 * @author Arina Buzdalova
 * 
 * @param <S> the type of a state
 * @param <A> the type of an action
 */
public class DelayedPrinter<S, A> implements AgentPrinter<S, A, DelayedAgent<S, A>> {
	private Writer writer;
	
	/**
	 * Constructs {@link DelayedPrinter} with the specified writer
	 * @param writer the specified writer
	 */
	private DelayedPrinter(Writer writer) {
		this.writer = writer;
	}
	
	/**
	 * Constructs this printer with the specified writer and adds it to the
	 * specified agent
	 * @param agent the agent to which the printer is added
	 * @param writer the specified writer
	 */
	public static<S, A> void addTo(DelayedAgent<S, A> agent, Writer writer) {
		agent.addPrinter(new DelayedPrinter<S, A>(writer));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void print(DelayedAgent<S, A> agent, Environment<? extends S, ? extends A> environment) {
		try {
            List<A> actions = agent.getEnvironment().getActions();
			writer.append(String.valueOf(agent.getSteps()));
			writer.append(" action: ");
			writer.append(String.valueOf(agent.getLastAction()));
			writer.append(" state: ");	
			writer.append(String.valueOf(environment.getCurrentState()));
			writer.append("\nQ:\n");			
			writer.append(Maps.format(agent.getQ(), actions, DOUBLE_0p3));
			writer.append("U:\n");
			writer.append(Maps.format(agent.getU(), actions, DOUBLE_0p3));
			writer.append("learn: \n");
			writer.append(Maps.format(agent.getLearn(), actions, PrintFormatter.BOOLEAN_01));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private static final PrintFormatter<Double> DOUBLE_0p3 = new PrintFormatter<Double>() {
        @Override
        public String format(Double value) {
            return String.format("%.3f ", value);
        }
    };
}