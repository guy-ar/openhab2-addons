/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.somfytahoma.internal.handler;

import static org.openhab.binding.somfytahoma.internal.SomfyTahomaBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;

/**
 * The {@link SomfyTahomaRollerShutterHandler} is responsible for handling commands,
 * which are sent to one of the channels of the roller shutter, screen & garage door
 * things.
 *
 * @author Ondrej Pecta - Initial contribution
 */
@NonNullByDefault
public class SomfyTahomaRollerShutterHandler extends SomfyTahomaBaseThingHandler {

    public SomfyTahomaRollerShutterHandler(Thing thing) {
        super(thing);
        stateNames.put(CONTROL, "core:ClosureState");
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        super.handleCommand(channelUID, command);
        if (!CONTROL.equals(channelUID.getId())) {
            return;
        }

        if (RefreshType.REFRESH.equals(command)) {
                return;
        } else {
            String cmd = getTahomaCommand(command.toString());
            if (COMMAND_MY.equals(cmd)) {
                String executionId = getCurrentExecutions();
                if (executionId != null) {
                    //Check if the roller shutter is moving and MY is sent => STOP it
                    cancelExecution(executionId);
                } else {
                    sendCommand(COMMAND_MY);
                }
            } else {
                String param = COMMAND_SET_CLOSURE.equals(cmd) ? "[" + command.toString() + "]" : "[]";
                sendCommand(cmd, param);
            }
        }
    }

    protected String getTahomaCommand(String command) {
        switch (command) {
            case "OFF":
            case "DOWN":
            case "CLOSE":
                return COMMAND_DOWN;
            case "ON":
            case "UP":
            case "OPEN":
                return COMMAND_UP;
            case "MY":
            case "STOP":
                return COMMAND_MY;
            default:
                return COMMAND_SET_CLOSURE;
        }
    }
}
