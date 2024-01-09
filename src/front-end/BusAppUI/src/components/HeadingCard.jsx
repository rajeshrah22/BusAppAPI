import React from 'react'
import Box from '@mui/material/Box'
import { useTheme } from '@emotion/react'
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'

export function HeadingCard({ inputState }) {

  const theme = useTheme()
  return (
    <Paper
      sx={{
        backgroundColor: theme.palette.primary.main,
        height: "15%",
        mb: 2,
        borderRadius: 1,
      }}
      elevation={4}
    >
      <Box
        sx={{
          p: 2,
        }}
      >
        <Typography
          color="white"
          variant="h4"
        >{inputState.showAgencies? "Agencies" : inputState.agencyTag.charAt(0).toUpperCase() + inputState.agencyTag.slice(1) }</Typography>
      </Box>
      <Box
        sx={{
          p: 2,
        }}
      >
        <Typography
          color="white"
          variant="body1"
        >
          {
            inputState.showAgencies
            ?
            "Select agency on map or in the list below"
            :
            "Select route from the list below"
          }
        </Typography>
      </Box>
    </Paper>
  )
}
