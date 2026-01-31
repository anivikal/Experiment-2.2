"""
LangChain/LangGraph Client for DateTime MCP Server

This script demonstrates how to connect to the DateTime MCP server
using langchain-mcp-adapters and create an agent with LangGraph.
"""

import asyncio
import os
from pathlib import Path

from dotenv import load_dotenv
from langchain_mcp_adapters.client import MultiServerMCPClient
from langgraph.prebuilt import create_react_agent
from langchain_anthropic import ChatAnthropic

# Load environment variables from .env file
load_dotenv()

# Get the absolute path to the datetime_server.py file
DATETIME_SERVER_PATH = str(Path(__file__).parent / "datetime_server.py")


async def main():
    """Main function to demonstrate the DateTime MCP client."""
    
    # Initialize the MCP client with the datetime server
    async with MultiServerMCPClient(
        {
            "datetime": {
                "transport": "stdio",  # Local subprocess communication
                "command": "python",
                "args": [DATETIME_SERVER_PATH],
            },
            # You can add more servers here, for example:
            # "weather": {
            #     "transport": "http",  # HTTP-based remote server
            #     "url": "http://localhost:8000/mcp",
            # }
        }
    ) as client:
        # Get all available tools from the MCP servers
        tools = client.get_tools()
        
        print("=" * 60)
        print("Available Tools from DateTime MCP Server:")
        print("=" * 60)
        for tool in tools:
            print(f"  - {tool.name}: {tool.description[:80]}...")
        print()
        
        # Create the LLM (requires ANTHROPIC_API_KEY environment variable)
        llm = ChatAnthropic(model="claude-sonnet-4-5-20250929")
        
        # Create a ReAct agent with the MCP tools
        agent = create_react_agent(llm, tools)
        
        # Example 1: Get current date and time
        print("=" * 60)
        print("Query 1: What is the current date and time?")
        print("=" * 60)
        response1 = await agent.ainvoke(
            {"messages": [{"role": "user", "content": "What is the current date and time?"}]}
        )
        print_response(response1)
        
        # Example 2: Get time in a specific timezone
        print("=" * 60)
        print("Query 2: What time is it in Tokyo?")
        print("=" * 60)
        response2 = await agent.ainvoke(
            {"messages": [{"role": "user", "content": "What time is it in Tokyo, Japan right now?"}]}
        )
        print_response(response2)
        
        # Example 3: Get formatted date
        print("=" * 60)
        print("Query 3: What day of the week is it?")
        print("=" * 60)
        response3 = await agent.ainvoke(
            {"messages": [{"role": "user", "content": "What day of the week is it today? Give me the full day name."}]}
        )
        print_response(response3)
        
        # Example 4: Compare times across timezones
        print("=" * 60)
        print("Query 4: Compare times in New York and London")
        print("=" * 60)
        response4 = await agent.ainvoke(
            {"messages": [{"role": "user", "content": "What time is it now in New York and London? What's the time difference?"}]}
        )
        print_response(response4)


def print_response(response):
    """Print the final response from the agent."""
    messages = response.get("messages", [])
    if messages:
        # Get the last message (the final response)
        last_message = messages[-1]
        print(f"\nAgent Response:\n{last_message.content}\n")


if __name__ == "__main__":
    # Check for API key
    if not os.getenv("ANTHROPIC_API_KEY"):
        print("Warning: ANTHROPIC_API_KEY environment variable not set.")
        print("Please set it in a .env file or export it:")
        print("  export ANTHROPIC_API_KEY='your-api-key'")
        print()
    
    # Run the async main function
    asyncio.run(main())
